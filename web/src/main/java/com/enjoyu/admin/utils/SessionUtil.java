package com.enjoyu.admin.utils;

import com.enjoyu.admin.common.exception.ServiceException;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Optional;

/**
 * session工具类
 */
public abstract class SessionUtil {

    public static HttpSession currentSession() {
        return currentRequest().getSession(false);
    }

    public static String currentSessionId() {
        HttpSession session = currentSession();
        return session != null ? session.getId() : null;
    }

    public static void setSessionAttribute(String key, Object value) {
        HttpSession session = currentSession();
        if (session != null) {
            session.setAttribute(key, value);
        }

    }

    public static Object getSessionAttribute(String key) {
        Object value = null;
        HttpSession session = currentSession();
        if (session != null) {
            value = session.getAttribute(key);
        }

        return value;
    }

    public static HttpServletRequest currentRequest() {
        return Optional.ofNullable(RequestContextHolder.getRequestAttributes())
                .map(attr -> (ServletRequestAttributes) attr)
                .map(ServletRequestAttributes::getRequest)
                .orElseThrow(() -> new ServiceException("错误的请求"));
    }

    public static String getRequestServletPath() {
        return currentRequest().getServletPath();
    }

    public static String getRequestContextPath() {
        return currentRequest().getContextPath();
    }
}
