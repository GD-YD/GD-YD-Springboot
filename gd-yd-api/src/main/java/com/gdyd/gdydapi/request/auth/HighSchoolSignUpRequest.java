package com.gdyd.gdydapi.request.auth;

import com.gdyd.gdydcore.domain.member.Grade;
import com.gdyd.gdydcore.domain.member.HighSchoolMajor;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydsupport.annotation.ValidEmail;
import com.gdyd.gdydsupport.annotation.ValidPassword;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "고등학생 회원가입 요청")
public record HighSchoolSignUpRequest(
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        @Schema(description = "이름", example = "이재영")
        String name,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Schema(description = "닉네임", example = "인천교회출신스님")
        String nickname,

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @ValidEmail(message = "올바르지 않은 이메일 형식입니다.")
        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @ValidPassword(message = "올바르지 않은 비밀번호 형식입니다. (8~16자, 영문 대소문자, 숫자, 특수문자를 모두 포함해야 합니다.)")
        @Schema(description = "비밀번호", example = "TestPassword123!")
        String password,

        @NotBlank(message = "고등학교 이름은 필수 입력 값입니다.")
        @Schema(description = "고등학교 이름", example = "기흥고등학교")
        String highSchoolName,

        @NotNull(message = "고등학교 학년은 필수 입력 값입니다.")
        @Schema(description = "고등학교 학년", example = "FIRST")
        Grade highSchoolGrade,

        @NotNull(message = "고등학교 전공은 필수 입력 값입니다.")
        @Schema(description = "고등학교 전공", example = "NATURAL_SCIENCE")
        HighSchoolMajor highSchoolMajor,

        @NotBlank(message = "고등학교 학번은 필수 입력 값입니다.")
        @Schema(description = "고등학교 학번", example = "2021-12345")
        String highSchoolStudentId,

        @NotBlank(message = "학생증 URL은 필수 입력 값입니다.")
        @Schema(description = "학생증 URL", example = "https://test.com/identification")
        String identificationUrl
) {
    public static HighSchoolStudent toHighSchoolStudent(HighSchoolSignUpRequest request) {
        return HighSchoolStudent.builder()
                .name(request.name())
                .nickname(request.nickname())
                .email(request.email())
                .password(request.password())
                .highSchoolName(request.highSchoolName())
                .highSchoolGrade(request.highSchoolGrade())
                .highSchoolMajor(request.highSchoolMajor())
                .highSchoolStudentId(request.highSchoolStudentId())
                .identificationUrl(request.identificationUrl())
                .build();
    }
}
