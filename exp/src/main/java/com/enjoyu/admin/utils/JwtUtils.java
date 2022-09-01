package com.enjoyu.admin.utils;

import com.enjoyu.admin.common.utils.DateUtil;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.TextCodec;

import static java.time.temporal.ChronoUnit.SECONDS;

/**
 * 使用对称加密算法+固定密钥的jwt工具类
 *
 * @author enjoyu
 */
public class JwtUtils {

    public static final String JWT_HTTP_HEADER = "Authorization";
    public static final String JWT_TOKEN_HEADER = "Bearer ";
    /**
     * 刷新时间间隔（秒）
     */
    public static final int REFRESH_INTERVAL = 300;
    /**
     * 失效时间间隔（秒）
     */
    public static final int EXPIRE_INTERVAL = 600;

    private static final SignatureAlgorithm ALG = SignatureAlgorithm.HS512;
    private static final String SEC = TextCodec.BASE64.encode("%^&YT$RF)GJN@#IN&N@OIN$JN$&#ON");

    public static String sign(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(DateUtil.now())
                .setExpiration(DateUtil.hourglass(SECONDS, EXPIRE_INTERVAL))
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
