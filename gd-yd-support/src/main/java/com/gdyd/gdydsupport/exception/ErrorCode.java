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
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "NOT_FOUND_REFRESH_TOKEN", "해당 리프레시 토큰을 찾을 수 없습니다."),
    INVALID_SIGNUP(HttpStatus.BAD_REQUEST, "INVALID_SIGNUP", "이미 가입되어 있는 회원입니다."),
    INVALID_LOGIN(HttpStatus.BAD_REQUEST, "INVALID_LOGIN", "아이디 또는 비밀번호가 일치하지 않습니다.");

    // AWS

    private final HttpStatus status;
    private final String code;
    private final String message;
}
