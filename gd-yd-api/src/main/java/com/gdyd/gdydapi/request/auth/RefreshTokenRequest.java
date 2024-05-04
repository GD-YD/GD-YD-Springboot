package com.gdyd.gdydapi.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "리프레시 토큰 재발급 또는 삭제를 위한 요청")
public record RefreshTokenRequest(
        @NotBlank(message = "리프레시 토큰은 필수입니다.")
        @Schema(description = "리프레시 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1...")
        String refreshToken
) {
}
