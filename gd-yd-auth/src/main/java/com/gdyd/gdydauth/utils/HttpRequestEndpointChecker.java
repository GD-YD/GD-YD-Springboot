package com.gdyd.gdydauth.utils;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;
import org.springframework.web.util.pattern.PathPattern;

import java.util.HashSet;
import java.util.Set;

@Component
public class HttpRequestEndpointChecker {
    private final Set<String> endpoints = new HashSet<>();
    private final AntPathMatcher pathMatcher = new AntPathMatcher();

    public HttpRequestEndpointChecker(@Qualifier("requestMappingHandlerMapping") RequestMappingHandlerMapping requestMappingHandlerMapping) {
        requestMappingHandlerMapping.getHandlerMethods().forEach((key, value) -> {
            if (key.getPathPatternsCondition() != null) {
                endpoints.addAll(key.getPathPatternsCondition().getPatterns()
                        .stream()
                        .map(PathPattern::getPatternString)
                        .toList());
            }
        });
    }

    public boolean isEndpointExist(String requestURI) {
        return endpoints.stream().anyMatch(pattern -> pathMatcher.match(pattern, requestURI));
    }

    public boolean isEndpointExist(HttpServletRequest request) {
        return isEndpointExist(request.getRequestURI());
    }

    public boolean isEndpointNotExist(HttpServletRequest request) {
        return !isEndpointExist(request.getRequestURI());
    }
}
