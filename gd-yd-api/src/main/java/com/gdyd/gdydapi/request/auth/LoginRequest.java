package com.gdyd.gdydapi.request.auth;

import com.gdyd.gdydsupport.annotation.ValidEmail;
import com.gdyd.gdydsupport.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "로그인 요청")
public record LoginRequest(
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @ValidEmail(message = "올바르지 않은 이메일 형식입니다.")
        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @ValidPassword(message = "올바르지 않은 비밀번호 형식입니다. (8~16자, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.)")
        @Schema(description = "비밀번호", example = "test1234!")
        String password
) {
}
