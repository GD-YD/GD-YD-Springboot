package com.gdyd.gdydapi.response.auth;

import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.UUID;

@Builder
@Schema(description = "회원가입 응답")
public record SignUpResponse(
        @Schema(description = "회원 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @Schema(description = "이름", example = "이재영")
        String name,

        @Schema(description = "닉네임", example = "인천교회출신스님")
        String nickName,

        @Schema(description = "학생증 식별 경로", example = "/identification/university/1234.jpg")
        String identificationPath
) {
    public static SignUpResponse from(HighSchoolStudent highSchoolStudent) {
        UUID uuid = UUID.randomUUID();
        String identificationPath = "/identification/high-school/" + uuid + "-" + highSchoolStudent.getId() + ".jpg";
        return SignUpResponse.builder()
                .id(highSchoolStudent.getId())
                .email(highSchoolStudent.getEmail())
                .name(highSchoolStudent.getName())
                .nickName(highSchoolStudent.getNickname())
                .identificationPath(identificationPath)
                .build();
    }

    public static SignUpResponse from(UniversityStudent universityStudent) {
        UUID uuid = UUID.randomUUID();
        String identificationPath = "/identification/university/" + uuid + "-" + universityStudent.getId() + ".jpg";
        return SignUpResponse.builder()
                .id(universityStudent.getId())
                .email(universityStudent.getEmail())
                .name(universityStudent.getName())
                .nickName(universityStudent.getNickname())
                .identificationPath(identificationPath)
                .build();
    }
}
