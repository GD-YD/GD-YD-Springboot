package com.gdyd.gdydapi.request.auth;

import com.gdyd.gdydcore.domain.member.Grade;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

@Schema(description = "대학생 회원가입 요청")
public record UniversitySignUpRequest(
        @NotBlank(message = "이름은 필수 입력 값입니다.")
        @Schema(description = "이름", example = "차승빈")
        String name,

        @NotBlank(message = "닉네임은 필수 입력 값입니다.")
        @Schema(description = "닉네임", example = "차라투스트라")
        String nickName,

        @NotBlank(message = "이메일은 필수 입력 값입니다.")
        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @NotBlank(message = "비밀번호는 필수 입력 값입니다.")
        @Schema(description = "비밀번호", example = "test1234!")
        String password,

        @NotBlank(message = "대학교 이름은 필수 입력 값입니다.")
        @Schema(description = "대학교 이름", example = "단국대학교")
        String universityName,

        @NotNull(message = "대학교 학년은 필수 입력 값입니다.")
        @Schema(description = "대학교 학년", example = "FIRST")
        Grade universityGrade,

        @NotBlank(message = "대학교 전공은 필수 입력 값입니다.")
        @Schema(description = "대학교 전공", example = "NATURAL_SCIENCE")
        String universityMajor,

        @NotBlank(message = "대학교 학번은 필수 입력 값입니다.")
        @Schema(description = "대학교 학번", example = "2021-12345")
        String universityStudentId,

        @NotBlank(message = "학생증 URL은 필수 입력 값입니다.")
        @Schema(description = "학생증 URL", example = "https://test.com/identification")
        String identificationUrl
) {
    public static UniversityStudent toUniversityStudent(UniversitySignUpRequest request) {
        return UniversityStudent.builder()
                .name(request.name())
                .nickName(request.nickName())
                .email(request.email())
                .password(request.password())
                .universityName(request.universityName())
                .universityGrade(request.universityGrade())
                .universityMajor(request.universityMajor())
                .universityStudentId(request.universityStudentId())
                .identificationUrl(request.identificationUrl())
                .build();
    }
}
