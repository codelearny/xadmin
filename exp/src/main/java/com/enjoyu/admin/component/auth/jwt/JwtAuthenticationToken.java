package com.enjoyu.admin.component.auth.jwt;

import com.enjoyu.admin.utils.JwtUtils;
import io.jsonwebtoken.Claims;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Date;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {

    private UserDetails principal;
    private final String token;
    private Claims claims;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
    }

    private JwtAuthenticationToken(JwtAuthenticationToken token, UserDetails principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = token.token;
        this.claims = token.claims;
        this.setAuthenticated(true);
    }


    @Override
    public Object getPrincipal() {
        return principal;
    }

    @Override
    public Object getCredentials() {
        return token;
    }

    public String getSubject() {
        return claims.getSubject();
    }

    public Claims getClaims() {
        return claims;
    }

    public Date getExp() {
        return claims.getExpiration();
    }

    public Date getIat() {
        return claims.getIssuedAt();
    }

    /**
     * 验签
     */
    public void verify() {
        claims = JwtUtils.verify(token);
    }

    /**
     * 认证通过
     */
    public JwtAuthenticationToken authenticated(UserDetails principal) {
        return new JwtAuthenticationToken(this, principal, principal.getAuthorities());
    }
}
