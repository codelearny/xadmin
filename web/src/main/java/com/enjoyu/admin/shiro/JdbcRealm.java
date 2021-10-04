package com.enjoyu.admin.shiro;

import com.enjoyu.admin.dao.entity.SysRole;
import com.enjoyu.admin.dao.entity.SysUser;
import com.enjoyu.admin.shiro.service.UserAuthService;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationException;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.crypto.hash.Md5Hash;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
public class JdbcRealm extends AuthorizingRealm {
    @Autowired
    UserAuthService userAuthService;

    /**
     * 认证
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken upToken = (UsernamePasswordToken) authcToken;
        String username = upToken.getUsername();
        if (username == null) {
            throw new AccountException("Null usernames are not allowed by this realm.");
        }
        SysUser u = userAuthService.user(username);
        if (u.getState() != 1) {
            throw new AuthenticationException("用户失效");
        }
        LocalDateTime lockTime = u.getLockTime();
        if (LocalDateTime.now().isAfter(lockTime)) {
            throw new AuthenticationException("账号锁定");
        }
        String credentials = u.getPassword();

        String source = u.getSalt();
        ByteSource credentialsSalt = new Md5Hash(source);
        return new SimpleAuthenticationInfo(u, credentials, credentialsSalt, getName());
    }

    /**
     * 授权
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        if (principals == null) {
            throw new AuthorizationException("PrincipalCollection method argument cannot be null.");
        }
        SysUser u = (SysUser) principals.getPrimaryPrincipal();

        List<SysRole> roles = userAuthService.getRolesForUser(u);
        Set<String> roleNames = new HashSet<>();
        List<Long> roleIds = new ArrayList<>();
        for (SysRole role : roles) {
            roleIds.add(role.getId());
            roleNames.add(role.getRoleName());
        }
        Set<String> permissions = userAuthService.getPermissions(roleIds);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo(roleNames);
        info.setStringPermissions(permissions);
        return info;
    }

    /**
     * 加密
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        HashedCredentialsMatcher md5CredentialsMatcher = new HashedCredentialsMatcher();
        md5CredentialsMatcher.setHashAlgorithmName(ShiroKit.hashAlgorithmName);
        md5CredentialsMatcher.setHashIterations(ShiroKit.hashIterations);
        super.setCredentialsMatcher(md5CredentialsMatcher);
    }
}
