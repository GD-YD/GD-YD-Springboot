package com.gdyd.gdydapi.request.auth;

import com.gdyd.gdydsupport.annotation.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "메일 인증 요청")
public record SendMailRequest(

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @ValidEmail(message = "올바르지 않은 이메일 형식입니다.")
        @Schema(description = "이메일", example = "test@gmail.com")
        String email
) {
}
