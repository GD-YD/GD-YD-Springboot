package com.gdyd.gdydauth.jwt;

import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String EXCEPTION_PROPERTY = "exception";
    private static final String PREFIX_BEARER = "Bearer";
    private static final String PREFIX_AUTHORIZATION = "Authorization";
    private static final String PREFIX_UN_AUTHORIZATION = "UnAuthorization";

    private final JwtProvider jwtProvider;

    private static final String[] JWT_WHITELIST = {
            "/swagger-ui",
            "/api-docs",
            "/api/v1/auth/login",
            "/api/v1/auth/signup",
            "/api/v1/auth/refresh",
            "/api/v1/auth/logout",
            "/api/v1/member/existing-email",
            "/api/v1/member/existing-nickname",
    };

    /**
     * OncePerRequestFilter to check the JWT token
     * @param request request
     * @param response response
     * @param filterChain filterChain
     */

    @Override
    protected void doFilterInternal(
            @Nonnull HttpServletRequest request,
            @Nonnull HttpServletResponse response,
            @Nonnull FilterChain filterChain
    ) throws ServletException, IOException {
        if (isWhiteListRequest(request)) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            String jwt = getJwtFromRequest(request);
            JwtValidationType jwtValidationType = jwtProvider.validateAccessToken(jwt);
            if (isValidJwtType(jwt, jwtValidationType) && jwtProvider.isAccessToken(jwt)) {
                Long userId = jwtProvider.getMemberIdFromAccessToken(jwt);
                UserAuthentication authentication = new UserAuthentication(userId, null, null);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
            } else {
                request.setAttribute(PREFIX_UN_AUTHORIZATION, jwtProvider.validateAccessToken(jwt));
                setErrorMessageInRequest(jwtProvider.validateAccessToken(jwt), request);
            }
        } catch (Exception e) {
            throw new BusinessException(ErrorCode.EMPTY_TOKEN);
        }
        filterChain.doFilter(request, response);
    }

    private String getJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(PREFIX_AUTHORIZATION);
        if (isValidBearerToken(bearerToken)) {
            return bearerToken.substring(PREFIX_BEARER.length());
        }
        throw new BusinessException(ErrorCode.UNSUPPORTED_BEARER_FORMAT);
    }

    private boolean isValidJwtType(String jwt, JwtValidationType jwtValidationType) {
        return StringUtils.hasText(jwt) && jwtValidationType == JwtValidationType.VALID_JWT;
    }

    private boolean isValidBearerToken(String token) {
        return StringUtils.hasText(token) && token.startsWith(PREFIX_BEARER);
    }

    private void setErrorMessageInRequest(JwtValidationType jwtValidationType, HttpServletRequest request) {
        switch (jwtValidationType) {
            case EMPTY_JWT -> request.setAttribute(EXCEPTION_PROPERTY, ErrorCode.EMPTY_TOKEN);
            case EXPIRED_JWT -> request.setAttribute(EXCEPTION_PROPERTY, ErrorCode.EXPIRED_JWT);
            case INVALID_JWT -> request.setAttribute(EXCEPTION_PROPERTY, ErrorCode.INVALID_JWT);
            case INVALID_JWT_SIGNATURE -> request.setAttribute(EXCEPTION_PROPERTY, ErrorCode.INVALID_JWT_SIGNATURE);
            case UNSUPPORTED_JWT -> request.setAttribute(EXCEPTION_PROPERTY, ErrorCode.UNSUPPORTED_JWT);
            default -> throw new IllegalArgumentException("Unknown JwtValidationType: " + jwtValidationType);
        }
    }

    /**
     * Check if the request is a Swagger request
     * @param request request
     * @return true if the request is a Swagger request
     */
    private boolean isWhiteListRequest(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        for (String uri : JWT_WHITELIST) {
            if (requestURI.startsWith(uri)) {
                return true;
            }
        }
        return false;
    }
}
