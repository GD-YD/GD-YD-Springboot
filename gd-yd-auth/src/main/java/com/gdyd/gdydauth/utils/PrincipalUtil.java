package com.gdyd.gdydauth.utils;

import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.Optional;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class PrincipalUtil {
    public static Long getMemberIdByPrincipal() {
        Object principal = Optional.ofNullable(SecurityContextHolder.getContext())
                .map(SecurityContext::getAuthentication)
                .map(Authentication::getPrincipal)
                .orElseThrow(() -> new BusinessException(ErrorCode.INVALID_MEMBER_PROFILE_REQUEST));
        try {
            return (long) principal;
        } catch (ClassCastException e) {
            throw new BusinessException(ErrorCode.INVALID_PRINCIPAL);
        }
    }
}
