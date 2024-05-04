package com.gdyd.gdydapi.response.auth;

import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "회원가입 응답")
public record SignUpResponse(
        @Schema(description = "회원 ID", example = "1")
        Long id,

        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @Schema(description = "이름", example = "이재영")
        String name,

        @Schema(description = "닉네임", example = "인천교회출신스님")
        String nickName
) {
    public static SignUpResponse from(HighSchoolStudent highSchoolStudent) {
        return new SignUpResponse(
                highSchoolStudent.getId(),
                highSchoolStudent.getEmail(),
                highSchoolStudent.getName(),
                highSchoolStudent.getNickName()
        );
    }

    public static SignUpResponse from(UniversityStudent universityStudent) {
        return new SignUpResponse(
                universityStudent.getId(),
                universityStudent.getEmail(),
                universityStudent.getName(),
                universityStudent.getNickName()
        );
    }
}
