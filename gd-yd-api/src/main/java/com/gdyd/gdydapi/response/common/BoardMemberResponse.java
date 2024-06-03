package com.gdyd.gdydapi.response.common;

import com.gdyd.gdydcore.domain.member.Grade;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import org.hibernate.Hibernate;

@Builder
@Schema(description = "게시판 멤버 응답")
public record BoardMemberResponse(
        @Schema(description = "닉네임", example = "인천교회출신스님")
        String nickname,

        @Schema(description = "학교명", example = "단국대학교")
        String schoolName,

        @Schema(description = "학년", example = "SECOND")
        Grade grade
) {
    public static BoardMemberResponse from(Member member) {
        return switch (member.getType()) {
            case UNIVERSITY_STUDENT -> from((UniversityStudent) Hibernate.unproxy(member));
            case HIGH_SCHOOL_STUDENT -> from((HighSchoolStudent) Hibernate.unproxy(member));
        };
    }

    public static BoardMemberResponse from(UniversityStudent member) {
        return BoardMemberResponse.builder()
                .nickname(member.getNickname())
                .schoolName(member.getUniversityName())
                .grade(member.getUniversityGrade())
                .build();
    }

    public static BoardMemberResponse from(HighSchoolStudent member) {
        return BoardMemberResponse.builder()
                .nickname(member.getNickname())
                .schoolName(member.getHighSchoolName())
                .grade(member.getHighSchoolGrade())
                .build();
    }
}
