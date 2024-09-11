package com.gdyd.gdydapi.response.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "코드 인증 응답")
public record VerifyCodeResponse(
        @Schema(description = "코드 일치 여부", example = "true")
        Boolean isMatch,

        @Schema(description = "코드 만료 여부", example = "false")
        Boolean isExpired
) {
    public static VerifyCodeResponse of(Boolean isMatch, Boolean isExpired) {
        return VerifyCodeResponse.builder()
                .isMatch(isMatch)
                .isExpired(isExpired)
                .build();
    }
}
