package com.gdyd.gdydapi.request.member;

import com.gdyd.gdydcore.domain.member.Grade;
import com.gdyd.gdydcore.domain.member.UniversityMajorCategory;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "프로필 업데이트 요청")
public record UpdateProfileRequest(
        @NotBlank(message = "닉네임은 필수 입력 값입니다.(닉네임은 다른 유저와 중복될 수 없습니다.)")
        @Schema(description = "닉네임", example = "인천교회출신스님")
        String nickname,

        @NotNull(message = "학년은 필수 입력 값입니다.")
        @Schema(description = "학년", example = "SECOND")
        Grade grade,

        @NotBlank(message = "프로필 이미지 URL은 필수 입력 값입니다.")
        @Schema(description = "프로필 이미지 URL", example = "DEFAULT")
        String profileImageUrl,

        @NotNull(message = "대학교 학과 카테고리는 필수 입력 값입니다.(고등학생은 DEFAULT로 설정하면 됩니다.)")
        @Schema(description = "대학교 학과 카테고리", example = "COMPUTER_SW")
        UniversityMajorCategory universityMajorCategory
) {
}
