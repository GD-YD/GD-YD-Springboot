package com.gdyd.gdydauth.utils;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.DispatcherServlet;
import org.springframework.web.servlet.HandlerExecutionChain;
import org.springframework.web.servlet.HandlerMapping;

@Component
@RequiredArgsConstructor
public class HttpRequestEndpointChecker {

    private final DispatcherServlet servlet;

    public boolean isEndpointNotExist(HttpServletRequest request) {
        if (servlet.getHandlerMappings() != null) {
            for (HandlerMapping handlerMapping : servlet.getHandlerMappings()) {
                try {
                    HandlerExecutionChain foundHandler = handlerMapping.getHandler(request);
                    if (foundHandler != null) {
                        return false;
                    }
                } catch (Exception e) {
                    return true;
                }
            }
        }
        return true;
    }
}
