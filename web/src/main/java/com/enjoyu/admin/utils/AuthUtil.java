package com.enjoyu.admin.utils;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Collections;
import java.util.Optional;

/**
 * auth工具类
 */
public abstract class AuthUtil {

    public static Object currentPrincipal() {
        Authentication authentication = currentAuthentication();
        if (authentication != null) {
            return authentication.getPrincipal();
        }
        return null;
    }

    private static Authentication currentAuthentication() {
        SecurityContext context = SecurityContextHolder.getContext();
        return context == null ? null : context.getAuthentication();
    }

    public static boolean hasRole(String roleName) {
        return Optional.ofNullable(currentAuthentication())
                .map(Authentication::getAuthorities)
                .orElse(Collections.emptyList())
                .stream()
                .map(GrantedAuthority::getAuthority)
                .anyMatch(au -> au.equals(roleName));
    }

}
