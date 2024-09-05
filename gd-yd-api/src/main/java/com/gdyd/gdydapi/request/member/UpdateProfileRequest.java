package com.gdyd.gdydapi.request.member;

import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydsupport.annotation.ValidEmail;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "프로필 업데이트 요청")
public record UpdateProfileRequest(
        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @ValidEmail(message = "올바르지 않은 이메일 형식입니다.")
        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Schema(description = "닉네임", example = "인천교회출신스님")
        String nickname
) {
}
