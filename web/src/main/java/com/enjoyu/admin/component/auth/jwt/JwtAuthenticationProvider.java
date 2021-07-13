package com.enjoyu.admin.component.auth.jwt;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

/**
 * 参考{@link DaoAuthenticationProvider}
 */
public class JwtAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userService;


    public JwtAuthenticationProvider(UserDetailsService userService) {
        this.userService = userService;
    }


    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        JwtAuthenticationToken token = (JwtAuthenticationToken) authentication;
        String subject = token.getSubject();
        UserDetails user = userService.loadUserByUsername(subject);
        if (user == null) {
            throw new BadCredentialsException("Bad JWT credentials");
        }
        return new JwtAuthenticationToken(user, token, user.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return JwtAuthenticationToken.class.isAssignableFrom(aClass);
    }
}
