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
    INVALID_MEMBER_TYPE(HttpStatus.BAD_REQUEST, "INVALID_MEMBER_TYPE", "유효하지 않은 회원 타입입니다."),
    INVALID_MEMBER_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_MEMBER_REQUEST", "해당 회원은 요청할 수 없습니다."),

    // LIKELIST
    ALREADY_LIKED(HttpStatus.BAD_REQUEST, "ALREADY_LIKED", "이미 좋아요를 누른 글입니다."),
    ALREADY_UNLIKED(HttpStatus.BAD_REQUEST, "ALREADY_UNLIKED", "이미 좋아요를 취소한 글입니다."),

    // SCRAPLIST
    ALREADY_SCRAPED(HttpStatus.BAD_REQUEST, "ALREADY_SCRAPED", "이미 스크랩을 누른 글입니다."),
    ALREADY_UNSCRAPED(HttpStatus.BAD_REQUEST, "ALREADY_UNSCRAPED", "이미 스크랩을 취소한 글입니다."),

    //MENTORING
    NOT_FOUND_HIGH_SCHOOL_STUDENT_QUESTION(HttpStatus.NOT_FOUND, "NOT_FOUND_HIGH_SCHOOL_STUDENT_QUESTION", "해당 고등학생 질문을 찾을 수 없습니다."),
    NOT_FOUND_UNIVERSITY_STUDENT_ANSWER(HttpStatus.NOT_FOUND, "NOT_FOUND_UNIVERSITY_STUDENT_ANSWER", "해당 대학생 답변을 찾을 수 없습니다."),

    //REPORT
    ALREADY_REPORTED(HttpStatus.BAD_REQUEST, "ALREADY_REPORTED", "이미 신고한 대상입니다."),

    //AUTH
    INVALID_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, "INVALID_REFRESH_TOKEN", "유효하지 않은 리프레시 토큰입니다."),
    EXPIRED_JWT(HttpStatus.UNAUTHORIZED, "EXPIRED_JWT_TOKEN", "만료된 토큰입니다."),
    INVALID_JWT_SIGNATURE(HttpStatus.UNAUTHORIZED, "INVALID_JWT_SIGNATURE", "토큰 생성키가 올바르지 않습니다."),
    INVALID_JWT(HttpStatus.UNAUTHORIZED, "INVALID_JWT_TOKEN", "올바르지 않는 토큰입니다."),
    UNSUPPORTED_JWT(HttpStatus.UNAUTHORIZED, "UNSUPPORTED_JWT_TOKEN", "지원하지 않는 토큰입니다."),
    EMPTY_TOKEN(HttpStatus.UNAUTHORIZED, "EMPTY_TOKEN", "유저 인증이 올바르지 않습니다."),
    NOT_FOUND_REFRESH_TOKEN(HttpStatus.NOT_FOUND, "NOT_FOUND_REFRESH_TOKEN", "해당 리프레시 토큰을 찾을 수 없습니다."),
    EMAIL_SEND_FAILURE(HttpStatus.INTERNAL_SERVER_ERROR, "EMAIL_SEND_FAILURE", "이메일 전송 중 오류가 발생했습니다."),
    INVALID_VERIFICATION_CODE(HttpStatus.NOT_FOUND, "INVALID_VERIFICATION_CODE", "잘못된 인증코드입니다."),
    NOT_FOUND_VERIFICATION_CODE(HttpStatus.NOT_FOUND, "NOT_FOUND_VERIFICATION_CODE", "해당 이메일로 생성 된 인증코드가 존재하지 않습니다."),

    CONTAINS_PROFANITY(HttpStatus.BAD_REQUEST, "CONTAINS_PROFANITY", "게시글에 욕설 또는 부적절한 언어가 포함되어 있습니다."),

    INVALID_SIGNUP(HttpStatus.BAD_REQUEST, "INVALID_SIGNUP", "이미 가입되어 있는 회원입니다."),
    INVALID_LOGIN(HttpStatus.BAD_REQUEST, "INVALID_LOGIN", "아이디 또는 비밀번호가 일치하지 않습니다."),

    UNSUPPORTED_BEARER_FORMAT(HttpStatus.UNAUTHORIZED, "UNSUPPORTED_BEARER_FORMAT", "지원하지 않는 Bearer token 형식입니다."),
    AUTHENTICATION_FORBIDDEN(HttpStatus.FORBIDDEN, "AUTHENTICATION_FORBIDDEN", "유효하지 않은 사용자 정보입니다."),

    INVALID_MEMBER_EMAIL(HttpStatus.BAD_REQUEST, "INVALID_MEMBER_EMAIL", "이미 가입되어 있는 회원입니다."),
    INVALID_MEMBER_PASSWORD(HttpStatus.BAD_REQUEST, "INVALID_MEMBER_PASSWORD", "비밀번호가 일치하지 않습니다."),

    INVALID_MEMBER_PROFILE_REQUEST(HttpStatus.BAD_REQUEST, "INVALID_MEMBER_PROFILE_REQUEST", "로그인이 되어있지 않고 프로필을 조회할 수 없습니다."),
    INVALID_PRINCIPAL(HttpStatus.BAD_REQUEST, "INVALID_PRINCIPAL", "로그인 객체의 정보가 유효하지 않거나, 존재하지 않습니다."),

    // POST
    NOT_FOUND_POST(HttpStatus.NOT_FOUND, "NOT_FOUND_POST", "해당 id를 가진 게시글이 존재하지 않습니다."),
    UNAUTHORIZED_MEMBER(HttpStatus.BAD_REQUEST, "UNAUTHORIZED_MEMBER", "권한이 없는 사용자 입니다"),

    // COMMENT
    NOT_FOUND_COMMENT(HttpStatus.NOT_FOUND, "NOT_FOUND_COMMENT", "해당 id를 가진 댓글이 존재하지 않습니다.");

    // AWS

    private final HttpStatus status;
    private final String code;
    private final String message;
}
