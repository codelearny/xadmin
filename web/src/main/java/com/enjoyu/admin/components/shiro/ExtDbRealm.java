package com.enjoyu.admin.components.shiro;

import com.enjoyu.admin.common.exception.ErrEnum;
import com.enjoyu.admin.components.mbp.entity.Role;
import com.enjoyu.admin.components.mbp.entity.Url;
import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.repository.SysPermissionRepository;
import com.enjoyu.admin.repository.SysRoleRepository;
import com.enjoyu.admin.repository.SysUserRepository;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@AllArgsConstructor
public class ExtDbRealm extends AuthorizingRealm {

    private final SysUserRepository userRepository;
    private final SysRoleRepository roleRepository;
    private final SysPermissionRepository permissionRepository;

    /**
     * 鉴权
     *
     * @param principals 认证主体
     * @return 拥有的权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        User u = (User) principals.getPrimaryPrincipal();

        List<Role> roles = roleRepository.getRolesByUserId(u.getId());
        Set<String> roleNames = new HashSet<>();
        List<Integer> roleIds = new ArrayList<>();
        for (Role role : roles) {
            roleIds.add(role.getId());
            roleNames.add(role.getRole());
        }
        List<Url> urls = permissionRepository.getPermissionsByRoleIds(roleIds);
        Set<String> permissions = new HashSet<>();
        for (Url url : urls) {
            permissions.add(url.getUrl());
        }
        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 认证
     *
     * @param authenticationToken 登录信息
     * @return 账户信息
     * @throws AuthenticationException 认证失败
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userRepository.user(token.getUsername());
        if (user == null) {
            throw new AuthenticationException(ErrEnum.ACCOUNT_NOT_FOUND.getMsg());
        }
        if (!user.getStatus()) {
            throw new AuthenticationException(ErrEnum.ACCOUNT_DISABLED.getMsg());
        }
        LocalDateTime lockedTime = user.getLockedTime();
        if (lockedTime != null) {
            LocalDateTime now = LocalDateTime.now();
            if (now.isBefore(lockedTime)) {
                throw new AuthenticationException(ErrEnum.ACCOUNT_LOCKED.getMsg());
            }
        }
        String password = user.getPassword();
        ByteSource bytes = ByteSource.Util.bytes(user.getSalt());
        return new SimpleAuthenticationInfo(user, password, bytes, getName());
    }

}
