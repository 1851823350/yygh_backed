package com.atwj.yygh.common.utils;

import io.jsonwebtoken.*;
import org.springframework.util.StringUtils;

import java.util.Date;

/**
 * @author 吴先森
 * @description:
 * @create 2022-10-09 13:25
 */
public class JwtHelper {
    private static long tokenExpiration = 24 * 60 * 60 * 1000; //token有效期时间
    private static String tokenSignKey = "123456";

    //根据参数得到token
    public static String createToken(Long userId, String userName) {
        String token = Jwts.builder()
                .setSubject("YYGH-USER")   //名称
                .setExpiration(new Date(System.currentTimeMillis() + tokenExpiration))//设置有效期时间（当前时间 + 往后延期时间）
                .claim("userId", userId) //主体信息
                .claim("userName", userName)
                .signWith(SignatureAlgorithm.HS512, tokenSignKey) //以下都是编码处理（签名hash）
                .compressWith(CompressionCodecs.GZIP)
                .compact();
        return token;
    }

    //根据token得到userId
    public static Long getUserId(String token) {
        if (StringUtils.isEmpty(token)) return null;
        Jws<Claims> claimsJws = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        Integer userId = (Integer) claims.get("userId");
        return userId.longValue();
    }

    //根据token得到userName
    public static String getUserName(String token) {
        if (StringUtils.isEmpty(token)) return "";
        Jws<Claims> claimsJws
                = Jwts.parser().setSigningKey(tokenSignKey).parseClaimsJws(token);
        Claims claims = claimsJws.getBody();
        return (String) claims.get("userName");
    }

    //测试
    public static void main(String[] args) {
        String token = JwtHelper.createToken(1L, "55");
        System.out.println(token);
        System.out.println(JwtHelper.getUserId(token));
        System.out.println(JwtHelper.getUserName(token));
    }
}
