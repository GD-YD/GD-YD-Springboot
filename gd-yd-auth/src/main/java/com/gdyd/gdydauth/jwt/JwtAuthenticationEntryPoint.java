package com.gdyd.gdydauth.jwt;

import com.gdyd.gdydauth.utils.HttpRequestEndpointChecker;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String REQUEST_ATTRIBUTE = "exception";
    private static final String RESPONSE_CODE_KEY = "code";
    private static final String RESPONSE_MESSAGE_KEY = "message";

    private final HttpRequestEndpointChecker endpointChecker;

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
        response.setStatus(HttpServletResponse.SC_OK);
        ErrorCode errorCode = (ErrorCode) request.getAttribute(REQUEST_ATTRIBUTE);

        if (endpointChecker.isEndpointNotExist(request)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
        }
        if (errorCode != null) {
            defineExceptionResponse(errorCode, response);
        }
    }

    public void generateExceptionHandler(ErrorCode errorCode, HttpServletResponse response) {
        try {
            setExceptionResponse(errorCode, response);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }



    private void defineExceptionResponse(ErrorCode errorCode, HttpServletResponse response) {
        switch (errorCode) {
            case EMPTY_TOKEN -> generateExceptionHandler(ErrorCode.EMPTY_TOKEN, response);
            case EXPIRED_JWT -> generateExceptionHandler(ErrorCode.EXPIRED_JWT, response);
            case INVALID_JWT_SIGNATURE -> generateExceptionHandler(ErrorCode.INVALID_JWT_SIGNATURE, response);
            case INVALID_JWT -> generateExceptionHandler(ErrorCode.INVALID_JWT, response);
            case UNSUPPORTED_JWT -> generateExceptionHandler(ErrorCode.UNSUPPORTED_JWT, response);
            default -> throw new IllegalArgumentException("Unexpected value: " + errorCode);
        }
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
