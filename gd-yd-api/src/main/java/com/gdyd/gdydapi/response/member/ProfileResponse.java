package com.gdyd.gdydapi.response.member;

import com.gdyd.gdydcore.domain.member.Grade;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "ProfileResponse", description = "프로필 응답")
public record ProfileResponse(
        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @Schema(description = "닉네임", example = "인천교회출신스님")
        String nickname,

        @Schema(description = "이름", example = "이재영")
        String name,

        @Schema(description = "학교 이름", example = "단국대학교")
        String schoolName,

        @Schema(description = "학년", example = "SECOND")
        Grade grade
) {
    public static ProfileResponse fromUniversityStudent(UniversityStudent student) {
        return ProfileResponse.builder()
                .email(student.getEmail())
                .nickname(student.getNickname())
                .name(student.getName())
                .schoolName(student.getUniversityName())
                .grade(student.getUniversityGrade())
                .build();
    }

    public static ProfileResponse fromHighSchoolStudent(HighSchoolStudent student) {
        return ProfileResponse.builder()
                .email(student.getEmail())
                .nickname(student.getNickname())
                .name(student.getName())
                .schoolName(student.getHighSchoolName())
                .grade(student.getHighSchoolGrade())
                .build();
    }
}
