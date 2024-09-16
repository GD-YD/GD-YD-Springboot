package com.gdyd.gdydauth.jwt;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
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

    public JwtValidationType validateAccessToken(String jwt) {
        return validateToken(jwt, accessTokenSecretKey);
    }

    public JwtValidationType validateRefreshToken(String jwt) {
        return validateToken(jwt, refreshTokenSecretKey);
    }

    public Long getMemberIdFromAccessToken(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getJwtSecretKey(accessTokenSecretKey))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return Long.valueOf(claims.getSubject());
    }

    public Long getMemberIdByRefreshToken(String jwt) {
        validateRefreshToken(jwt);
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getJwtSecretKey(refreshTokenSecretKey))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return Long.valueOf(claims.getSubject());
    }

    public boolean isAccessToken(String jwt) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(getJwtSecretKey(accessTokenSecretKey))
                .build()
                .parseClaimsJws(jwt)
                .getBody();
        return claims.getId().equals(JwtType.ACCESS_TOKEN.name());
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

    private JwtValidationType validateToken(String jwt, String secretKey) {
        if (jwt == null || jwt.isEmpty()) {
            return JwtValidationType.EMPTY_JWT;
        }
        try {
            Jwts.parserBuilder().setSigningKey(getJwtSecretKey(secretKey)).build().parseClaimsJws(jwt);
            return JwtValidationType.VALID_JWT;
        } catch (SignatureException e) {
            return JwtValidationType.INVALID_JWT_SIGNATURE;
        } catch (MalformedJwtException e) {
            return JwtValidationType.INVALID_JWT;
        } catch (ExpiredJwtException e) {
            return JwtValidationType.EXPIRED_JWT;
        } catch (UnsupportedJwtException e) {
            return JwtValidationType.UNSUPPORTED_JWT;
        } catch (IllegalArgumentException e) {
            return JwtValidationType.EMPTY_JWT;
        }
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
