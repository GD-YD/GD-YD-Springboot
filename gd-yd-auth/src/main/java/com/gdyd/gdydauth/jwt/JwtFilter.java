package com.gdyd.gdydauth.jwt;

import com.gdyd.gdydauth.utils.HttpRequestEndpointChecker;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import jakarta.annotation.Nonnull;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {
    private static final String EXCEPTION_PROPERTY = "exception";
    private static final String PREFIX_BEARER = "Bearer";
    private static final String PREFIX_AUTHORIZATION = "Authorization";
    private static final String PREFIX_UN_AUTHORIZATION = "UnAuthorization";

    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String RESPONSE_CODE_KEY = "code";
    private static final String RESPONSE_MESSAGE_KEY = "message";

    private final JwtProvider jwtProvider;
    private final HttpRequestEndpointChecker endpointChecker;

    private static final String[] JWT_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**",
            "/v3/api-docs/**",
            "/swagger-ui/**",
            "/api-docs/json/**",

            "/api/v1/auth/login",
            "/api/v1/auth/signup/**",
            "/api/v1/auth/refresh",
            "/api/v1/auth/send-verification-email",
            "/api/v1/auth/verify-code",
            "/api/v1/auth/forgot-password",
            "/api/v1/auth/forgot-password/**",
            "/api/v1/member/existing-email",
            "/api/v1/member/existing-nickname",

            "/h2-console",
            "/h2-console/**"
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
        if (isWhiteListRequest(request) || endpointChecker.isEndpointNotExist(request)) {
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
                filterChain.doFilter(request, response);
            } else {
                request.setAttribute(PREFIX_UN_AUTHORIZATION, jwtProvider.validateAccessToken(jwt));
                setErrorMessageInRequest(jwtProvider.validateAccessToken(jwt), request);
            }
        } catch (BusinessException e) {
            setExceptionResponse(e.getErrorCode(), response);
        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "유효하지 않은 인증 정보입니다.");
        }
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

    private List<RequestMatcher> permittedWhiteList() {
        return Stream.of(JWT_WHITELIST)
                .map(AntPathRequestMatcher::new)
                .collect(Collectors.toList());
    }

    public boolean isWhiteListRequest(HttpServletRequest request) {
        return permittedWhiteList().stream()
                .anyMatch(matcher -> matcher.matches(request));
    }

    private void setExceptionResponse(ErrorCode errorCode, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

        JSONObject responseBody = new JSONObject();
        responseBody.put(RESPONSE_CODE_KEY, errorCode.getCode());
        responseBody.put(RESPONSE_MESSAGE_KEY, errorCode.getMessage());

        response.getWriter().print(responseBody);
    }
}
