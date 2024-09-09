package com.gdyd.gdydapi.response.auth;

import com.gdyd.gdydauth.jwt.Token;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "이메일 전송 응답")
public record SendMailResponse(
        @Schema(description = "인증 코드", example = "1a2B3c")
        String code
) {
    public static SendMailResponse of(String code) {
        return SendMailResponse.builder()
                .code(code)
                .build();
    }
}
