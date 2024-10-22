package com.gdyd.gdydapi.response.mentoring;

import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "고등학생 질문 매칭 추천 응답")
public record RecommendationHighSchoolStudentQuestionResponse(
        @Schema(description = "매칭된 고등학생 질문글 ID", example = "1")
        Long id,

        @Schema(description = "매칭된 고등학생 질문글 제목", example = "제가 이 학교를 갈 수 있을까요?")
        String title,

        @Schema(description = "매칭된 고등학생 질문글 내용", example = "현재 수능 23225 입니다.")
        String question,

        @Schema(description = "매칭된 고등학생 질문글에서 답변을 원하는 대학생의 학교명", example = "단국대학교")
        String universityNameTag,

        @Schema(description = "매칭된 고등학생 질문글에서 답변을 원하는 대학생의 학과", example = "컴퓨터/SW")
        String universityMajorTag,

        @Schema(description = "매칭된 고등학생 질문글에서 답변을 원하는 대학생의 학년", example = "1학년")
        String universityGradeTag
) {
    public static RecommendationHighSchoolStudentQuestionResponse from(HighSchoolStudentQuestion highSchoolStudentQuestion) {
        return RecommendationHighSchoolStudentQuestionResponse.builder()
                .id(highSchoolStudentQuestion.getId())
                .title(highSchoolStudentQuestion.getTitle())
                .question(highSchoolStudentQuestion.getQuestion())
                .universityNameTag(highSchoolStudentQuestion.getUniversityNameTag())
                .universityMajorTag(highSchoolStudentQuestion.getUniversityMajorTag().getValue())
                .universityGradeTag(highSchoolStudentQuestion.getUniversityGradeTag().getValue())
                .build();
    }
}
