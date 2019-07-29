package com.extlight.common.utils;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.*;

import java.security.Key;
import java.util.Date;
import java.util.Map;

/**
 * @Author MoonlgihtL
 * @ClassName: TokenUtil
 * @ProjectName freedom-boot
 * @Description: JWT 工具
 * @Date 2019/7/5 17:15
 */
public class TokenUtil {

    private static Key key = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    /**
     * 过期时间 20 分钟
     */
    private static final Long EXPIRE_TIME = 20 * 60 * 1000L;

    public static String createToken(Long userId, String username, Map<String, Object> claims) {
        long now = System.currentTimeMillis();
        JwtBuilder builder= Jwts.builder()
                .setId(userId.toString())
                .setIssuedAt(new Date(now))
                .setExpiration(new Date(now + EXPIRE_TIME))
                .setSubject(username);

        if (claims != null && !claims.isEmpty()) {
            claims.entrySet().forEach(k -> builder.claim(k.getKey(), k.getValue()));
        }

        builder.signWith(key);
        return builder.compact();
    }

    public static Claims  getClaims(String token) {
        try {
            Jws<Claims> claimsJws = Jwts.parser().setSigningKey(key).parseClaimsJws(token);
            return claimsJws.getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
