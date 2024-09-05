package com.gdyd.gdydapi.response.mentoring;

import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "고등학생 질문 응답")
public record HighSchoolStudentQuestionResponse(
        @Schema(description = "고등학생 질문 ID", example = "1")
        Long id,

        @Schema(description = "고등학생 닉네임", example = "궁금한 고등학생2")
        String highSchoolStudentNickname,

        @Schema(description = "고등학생 질문 제목", example = "이 성적으로 어떤 대학을 갈수 있을까요?")
        String title,

        @Schema(description = "고등학생 질문 내용", example = "이번에 모의고사 성적이 나왔는데, 이 성적으로 어떤 대학을 갈수 있을까요?")
        String question,

        @Schema(description = "고등학생 질문 등록일", example = "2021-10-01T00:00:00")
        String createdAt
) {
    public static HighSchoolStudentQuestionResponse from(
            HighSchoolStudentQuestion highSchoolStudentQuestion
    ) {
        return HighSchoolStudentQuestionResponse.builder()
                .id(highSchoolStudentQuestion.getId())
                .highSchoolStudentNickname(highSchoolStudentQuestion.getHighSchoolStudent().getNickname())
                .title(highSchoolStudentQuestion.getTitle())
                .question(highSchoolStudentQuestion.getQuestion())
                .createdAt(highSchoolStudentQuestion.getCreatedAt().toString())
                .build();
    }
}
