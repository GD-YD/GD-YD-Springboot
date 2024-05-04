package com.gdyd.gdydauth.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Date;

@Component
@RequiredArgsConstructor
public class JwtProvider {
    /**
     * Value  : openssl rand -hex 64
     */
    @Value("${jwt.access-token-secret-key}")
    private String accessTokenSecretKey;

    @Value("${jwt.refresh-token-secret-key}")
    private String refreshTokenSecretKey;

    @Value("${jwt.access-token-expire-time}")
    private long accessTokenExpirationTime;

    @Value("${jwt.refresh-token-expire-time}")
    private long refreshTokenExpirationTime;

    public Token generateAccessToken(Authentication authentication) {
        return generateToken(authentication, accessTokenSecretKey, accessTokenExpirationTime, JwtType.ACCESS_TOKEN);
    }

    public Token generateRefreshToken(Authentication authentication) {
        return generateToken(authentication, refreshTokenSecretKey, refreshTokenExpirationTime, JwtType.REFRESH_TOKEN);
    }

    private Token generateToken(Authentication authentication, String secretKey, long expirationTime, JwtType jwtType) {
        Date expiredTime = getExpirationDate(expirationTime);
        Claims claims = Jwts.claims()
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setId(jwtType.name());
        String token = Jwts.builder()
                .setClaims(claims)
                .setSubject(String.valueOf(authentication.getPrincipal()))
                .setIssuedAt(new Date())
                .setExpiration(expiredTime)
                .signWith(getJwtSecretKey(secretKey), SignatureAlgorithm.HS512)
                .setId(jwtType.name())
                .compact();
        return Token.builder()
                .value(token)
                .expiredTime(expiredTime)
                .build();
    }

    private SecretKey getJwtSecretKey(String secretKey) {
        String encodedKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
        return Keys.hmacShaKeyFor(encodedKey.getBytes());
    }

    private Date getExpirationDate(long tokenExpirationTime) {
        long currentTime = System.currentTimeMillis();
        return new Date(currentTime + tokenExpirationTime);
    }
}
