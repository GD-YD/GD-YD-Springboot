package com.gdyd.gdydsupport.exception;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ErrorResponse {

    @JsonIgnore
    private HttpStatus status;

    private String code;

    private String message;

    private List<FieldError> fieldErrors = new ArrayList<>();

    @Builder
    private ErrorResponse(final HttpStatus status, final String code, final String message, List<FieldError> fieldErrors) {
        this.status = status;
        this.code = code;
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    public static ErrorResponse of(ErrorCode errorCode) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, String message) {
        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(message)
                .build();
    }

    public static ErrorResponse of(ErrorCode errorCode, Map<String, String> fieldErrors) {
        List<FieldError> errors = fieldErrors.entrySet().stream()
                .map(entry -> new FieldError(entry.getKey(), entry.getValue())).toList();

        return ErrorResponse.builder()
                .status(errorCode.getStatus())
                .code(errorCode.getCode())
                .message(errorCode.getMessage())
                .fieldErrors(errors)
                .build();
    }

    @Getter
    @NoArgsConstructor(access = AccessLevel.PROTECTED)
    protected static class FieldError {
        private String field;
        private String message;

        public FieldError (final String field, final String message) {
            this.field = field;
            this.message = message;
        }
    }
}