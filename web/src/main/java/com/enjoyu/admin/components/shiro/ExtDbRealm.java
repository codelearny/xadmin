package com.enjoyu.admin.components.shiro;

import com.enjoyu.admin.common.exception.ErrEnum;
import com.enjoyu.admin.components.mbp.entity.User;
import com.enjoyu.admin.components.mbp.service.IUserService;
import lombok.AllArgsConstructor;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;

import java.time.LocalDateTime;

@AllArgsConstructor
public class ExtDbRealm extends AuthorizingRealm {

    private final IUserService userService;

    /**
     * 鉴权
     *
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {

        return null;
    }

    /**
     * 认证
     *
     * @param authenticationToken 登录信息
     * @return 账户信息
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        User user = userService.user(token.getUsername());
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
