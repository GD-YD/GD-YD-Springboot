package com.gdyd.gdydapi.response.member;

import com.gdyd.gdydapi.response.common.BoardMemberResponse;
import com.gdyd.gdydcore.domain.member.*;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.hibernate.Hibernate;

@Builder
@Schema(name = "ProfileResponse", description = "프로필 응답")
public record ProfileResponse(
        @Schema(description = "회원 유형", example = "UNIVERSITY_STUDENT")
        MemberType memberType,

        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @Schema(description = "닉네임", example = "인천교회출신스님")
        String nickname,

        @Schema(description = "이름", example = "이재영")
        String name,

        @Schema(description = "학교 이름", example = "단국대학교")
        String schoolName,

        @Schema(description = "과 이름", example = "컴퓨터공학과")
        String major,

        @Schema(description = "학년", example = "SECOND")
        Grade grade,

        @Schema(description = "입학년도", example = "2021")
        Long enterYear
) {
    public static ProfileResponse from(Member member) {
        return switch (member.getType()) {
            case UNIVERSITY_STUDENT -> from((UniversityStudent) Hibernate.unproxy(member));
            case HIGH_SCHOOL_STUDENT -> from((HighSchoolStudent) Hibernate.unproxy(member));
            default -> throw new BusinessException(ErrorCode.INVALID_MEMBER_TYPE);
        };
    }
    public static ProfileResponse from(UniversityStudent student) {
        return ProfileResponse.builder()
                .memberType(student.getType())
                .email(student.getEmail())
                .nickname(student.getNickname())
                .name(student.getName())
                .schoolName(student.getUniversityName())
                .major(student.getUniversityMajor())
                .grade(student.getUniversityGrade())
                .enterYear(student.getEnterYearUniversity())
                .build();
    }

    public static ProfileResponse from(HighSchoolStudent student) {
        return ProfileResponse.builder()
                .memberType(student.getType())
                .email(student.getEmail())
                .nickname(student.getNickname())
                .name(student.getName())
                .schoolName(student.getHighSchoolName())
                .major(student.getHighSchoolMajor().toString())
                .grade(student.getHighSchoolGrade())
                .enterYear(student.getEnterYearHighSchool())
                .build();
    }
}
