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
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN", "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "EXPIRED_JWT_TOKEN", "만료된 토큰입니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "INVALID_JWT_SIGNATURE", "토큰 생성키가 올바르지 않습니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "INVALID_JWT_TOKEN", "올바르지 않는 토큰입니다."),
    UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "UNSUPPORTED_JWT_TOKEN", "지원하지 않는 토큰입니다."),
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "EMPTY_TOKEN", "유저 인증이 올바르지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "NOT_FOUND_REFRESH_TOKEN", "해당 리프레시 토큰을 찾을 수 없습니다."),
    INVALID_SIGNUP(HttpStatus.BAD_REQUEST, "INVALID_SIGNUP", "이미 가입되어 있는 회원입니다."),
    INVALID_LOGIN(HttpStatus.BAD_REQUEST, "INVALID_LOGIN", "아이디 또는 비밀번호가 일치하지 않습니다.");

    // AWS

    private final HttpStatus status;
    private final String code;
    private final String message;
}
