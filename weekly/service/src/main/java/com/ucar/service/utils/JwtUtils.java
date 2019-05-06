package com.ucar.service.utils;

import com.ucar.api.dto.UserToken;
import com.ucar.service.constants.CommonConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.util.Date;

/**
 * @author: cong.zhou01
 * @description: JWT工具类
 * @date: 2018/8/7 10:19
 */
public class JwtUtils {

    public static String generateToken(UserToken userToken, int expire) throws Exception {
        String token = Jwts.builder()
                .setSubject(userToken.getUserName())
                .claim(CommonConstants.CONTEXT_USER_ID, userToken.getUserId())
                .claim(CommonConstants.RENEWAL_TIME, new Date(System.currentTimeMillis() + expire / 2))
                .setExpiration(new Date(System.currentTimeMillis() + expire))
                .signWith(SignatureAlgorithm.HS256, CommonConstants.JWT_PRIVATE_KEY)
                .compact();
        return token;
    }


    public static UserToken getInfoFromToken(String token) throws Exception {
        Claims claims = Jwts.parser()
                .setSigningKey(CommonConstants.JWT_PRIVATE_KEY).parseClaimsJws(token)
                .getBody();
        UserToken userToken = new UserToken();
        userToken.setUserName(claims.getSubject());
        userToken.setUserId(Integer.valueOf(claims.get(CommonConstants.CONTEXT_USER_ID).toString()));
        return userToken;
    }
}
