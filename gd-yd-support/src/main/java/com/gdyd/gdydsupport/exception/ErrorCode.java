package com.gdyd.gdydsupport.exception;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ErrorCode {
    // COMMON
    INTERNAL_SERVER_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "INTERNAL_SERVER_ERROR", "오류가 발생했습니다."),
    INVALID_INPUT_VALUE(HttpStatus.BAD_REQUEST, "INVALID_INPUT_VALUE", "유효하지 않은 입력입니다."),

    //MEMBER
    NOT_FOUND_MEMBER(HttpStatus.NOT_FOUND, "NOT_FOUND_MEMBER", "해당 회원을 찾을 수 없습니다."),

    //AUTH
    INVALID_LOGIN(HttpStatus.BAD_REQUEST, "INVALID_LOGIN", "아이디 또는 비밀번호가 일치하지 않습니다.");

    // AWS

    private final HttpStatus status;
    private final String code;
    private final String message;
}
