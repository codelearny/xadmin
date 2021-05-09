package com.enjoyu.admin.utils;

import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;

public class WebIPUtil {

    public static final String[] IP_HEADERS = {"X-Forwarded-For", "X-Real-IP", "Proxy-Client-IP", "WL-Proxy-Client-IP", "HTTP_CLIENT_IP", "HTTP_X_FORWARDED_FOR"};

    /**
     * 从HTTP HEADER中取得ip地址 ，多次反向代理后会有多个ip值，第一个ip才是真实ip
     *
     * @param request Servlet请求
     * @return ip
     */
    public static String extractIpAddress(HttpServletRequest request) {
        String XFor;
        for (String ipHeader : IP_HEADERS) {
            XFor = request.getHeader(ipHeader);
            if (StringUtils.hasText(XFor) && !"unKnown".equalsIgnoreCase(XFor)) {
                int index = XFor.indexOf(",");
                if (index > 0) {
                    return XFor.substring(0, index);
                }
                return XFor;
            }
        }
        return request.getRemoteAddr();
    }
}
