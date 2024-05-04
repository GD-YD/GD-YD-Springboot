package com.gdyd.gdydapi.request.auth;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청")
public record LoginRequest(
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Schema(description = "비밀번호", example = "test1234!")
        String password
) {
}
