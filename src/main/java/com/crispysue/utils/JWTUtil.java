package com.crispysue.utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

public class JWTUtil {
    /**
     * 生成 jwt 密令
     * 使用 Hs256 算法，密匙使用固定密钥
     *
     * @param secretKey jwt 密钥
     * @param ttlMillis jwt 过期时间（毫秒）
     * @param claims 设置的信息
     */
    public static String createJWT(String secretKey, long ttlMillis, Map<String,Object> claims) {
        // 指定签名的时候使用的签名算法，也就是 header 的部分
        SignatureAlgorithm signatureAlgorithm = SignatureAlgorithm.HS256;

        // 生成 JWT 的时间
        long expMillis = System.currentTimeMillis() + ttlMillis;
        Date exp = new Date(expMillis);

        JwtBuilder builder = Jwts.builder()
                // 设置 jwt 的 负载
                .setClaims(claims)
                // 设置签名使用的签名算法和签名使用的密钥
                .signWith(signatureAlgorithm, secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置过期时间
                .setExpiration(exp);

        return builder.compact();
    }

    /**
     * token 解密
     * @param secretKey
     * @param token
     * @return
     */
    public static Claims parseJWT(String secretKey, String token){
        // 得到 DefaultJWTParser
        Claims claims = Jwts.parser()
                // 设置签名的密钥
                .setSigningKey(secretKey.getBytes(StandardCharsets.UTF_8))
                // 设置需要解析的 jwt
                .parseClaimsJws(token)
                // 获取 token 的负载
                .getBody();
        return claims;
    }
}