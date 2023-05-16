package com.chunyang.demobackend.util;

import com.chunyang.demobackend.common.AuthConstant;
import com.chunyang.demobackend.dto.UserDTO;
import com.chunyang.demobackend.entity.UserEntity;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Data
@Component
public class TokenUtil implements AuthConstant {

    private static final Logger LOGGER = LoggerFactory.getLogger(TokenUtil.class);

    private static Claims getClaimsFromToken(String token) {
        if (token == null) {
            return null;
        }
        Claims claims = null;
        try {
            claims = Jwts.parser()
                    .setSigningKey(TOKEN_SECRET)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return claims;
    }


    public static String generateToken(UserEntity user) {
        Map<String, Object> claims = new HashMap<>();
        claims.put(CLAIM_KEY_USER_ID, user.getId());
        claims.put(CLAIM_KEY_USER_EMAIL, user.getEmail());
        claims.put(CLAIM_KEY_USERNAME, user.getName());
        claims.put(CLAIM_KEY_CREATE_TIME, new Date());

        return generateToken(claims);
    }

    private static String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, TOKEN_SECRET)
                .compact();
    }


    private static Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + TOKEN_EXPIRATION * 1000);
    }


    public static String getUsernameFromToken(String token) {
        String username = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            username = claims == null ? null : claims.getSubject();
        } catch (Exception e) {
            LOGGER.error("parse token to get username got an exception，error is：{}", e);
        }
        return username;
    }

    public static UserDTO getUserFromToken(String token) {
        Claims claims = TokenUtil.getClaimsFromToken(token);
        Integer userId = claims.get(CLAIM_KEY_USER_ID, Integer.class);
        String userName = claims.get(CLAIM_KEY_USERNAME, String.class);

        UserDTO userDTO = new UserDTO();
        userDTO.setId(userId);
        userDTO.setName(userName);

        return userDTO;
    }

    public static Boolean isTokenExpired(String token) {
        final Date expiration = getExpirationDateFromToken(token);
        if (expiration == null) {
            return true;
        }
        return expiration.before(new Date());
    }


    public static Date getExpirationDateFromToken(String token) {
        Date expiration = null;
        try {
            final Claims claims = getClaimsFromToken(token);
            if (claims != null) {
                expiration = claims.getExpiration();
            }
        } catch (Exception e) {
            LOGGER.error("when getting token expiration time got an exception，error is：{}", e);
        }
        return expiration;
    }


    public static String refreshToken(String token) {
        final Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            claims.put(CLAIM_KEY_CREATE_TIME, new Date());
        }
        return generateToken(claims);
    }
}
