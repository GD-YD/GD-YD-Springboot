package com.gdyd.gdydapi.request.auth;

import com.gdyd.gdydsupport.annotation.ValidEmail;
import com.gdyd.gdydsupport.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "비밀번호 리셋 요청")
public record ResetPasswordRequest(

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @ValidEmail(message = "올바르지 않은 이메일 형식입니다.")
        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @ValidPassword(message = "올바르지 않은 비밀번호 형식입니다. (8~16자, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.)")
        @Schema(description = "새로운 비밀번호", example = "NewPassword123!")
        String newPassword,

        @NotBlank(message = "정상적인 비밀번호 리셋 요청 확인을 위한 인증코드입니다.")
        @Schema(description = "인증코드", example = "1a2B3c")
        String code
) {
}
