package com.gdyd.gdydauth.jwt;

import com.gdyd.gdydauth.utils.HttpRequestEndpointChecker;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    private static final String CONTENT_TYPE = "application/json;charset=UTF-8";
    private static final String RESPONSE_CODE_KEY = "code";
    private static final String RESPONSE_MESSAGE_KEY = "message";

    private final HttpRequestEndpointChecker endpointChecker;

    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException {
        if (endpointChecker.isEndpointNotExist(request)) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Resource not found");
        }

        generateExceptionHandler(ErrorCode.AUTHENTICATION_FORBIDDEN, response);
    }

    public void generateExceptionHandler(ErrorCode errorCode, HttpServletResponse response) {
        try {
            setExceptionResponse(errorCode, response);
        } catch (IOException e) {
            throw new BusinessException(ErrorCode.INTERNAL_SERVER_ERROR);
        }
    }

    private void setExceptionResponse(ErrorCode errorCode, HttpServletResponse response) throws IOException {
        response.setContentType(CONTENT_TYPE);
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);

        JSONObject responseBody = new JSONObject();
        responseBody.put(RESPONSE_CODE_KEY, errorCode.getCode());
        responseBody.put(RESPONSE_MESSAGE_KEY, errorCode.getMessage());

        response.getWriter().print(responseBody);
    }
}
