package com.gdyd.gdydapi.response.mentoring;

import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "고등학생 질문 등록 응답")
public record CreateHighSchoolStudentQuestionResponse(
        @Schema(description = "고등학생 질문 ID", example = "1")
        Long highSchoolStudentQuestionId,

        @Schema(description = "고등학생 질문 제목", example = "이 정도 등급으로 입학할 수 있을까요?")
        String title,

        @Schema(description = "고등학생 질문 내용", example = "12344로 해당 대학에 입학할 수 있을까요?")
        String question
) {
    public static CreateHighSchoolStudentQuestionResponse from(HighSchoolStudentQuestion highSchoolStudentQuestion) {
        return CreateHighSchoolStudentQuestionResponse.builder()
                .highSchoolStudentQuestionId(highSchoolStudentQuestion.getId())
                .title(highSchoolStudentQuestion.getTitle())
                .question(highSchoolStudentQuestion.getQuestion())
                .build();
    }
}
