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
    private String token;
    private Claims claims;

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        this.claims = JwtUtils.verify(token);
        this.setAuthenticated(false);
    }

    public JwtAuthenticationToken(UserDetails principal, JwtAuthenticationToken token, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        this.token = (String) token.getCredentials();
        this.claims = token.getClaims();
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
}
