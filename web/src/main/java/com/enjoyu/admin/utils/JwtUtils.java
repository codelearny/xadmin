package com.enjoyu.admin.utils;

import com.enjoyu.admin.common.utils.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import java.util.Date;

import static java.time.temporal.ChronoUnit.MINUTES;

/**
 * 使用对称加密算法+固定密钥的jwt工具类
 *
 * @author enjoyu
 */
public class JwtUtils {

    public static final String JWT_HTTP_HEADER = "Authorization";
    public static final String JWT_TOKEN_HEADER = "Bearer ";

    private static final SignatureAlgorithm ALG = SignatureAlgorithm.HS512;
    private static final String SEC = TextCodec.BASE64.encode("%^&YT$RF)GJN@#IN&N@OIN$JN$&#ON");

    public static String sign(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(DateUtil.hourglass(MINUTES, 10))
                .signWith(ALG, SEC)
                .compact();
    }

    public static Claims verify(String token) {
        return Jwts.parser()
                .setSigningKey(SEC)
                .parseClaimsJws(token)
                .getBody();
    }

    public static String getTokenFromHttpHeader(String authHeader) {
        if (authHeader == null) {
            return null;
        }
        if (authHeader.startsWith(JWT_TOKEN_HEADER)) {
            return authHeader.substring(JWT_TOKEN_HEADER.length());
        }
        return null;
    }


}
