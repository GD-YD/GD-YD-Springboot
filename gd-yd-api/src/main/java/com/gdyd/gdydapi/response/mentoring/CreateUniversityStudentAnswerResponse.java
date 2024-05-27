package com.gdyd.gdydapi.response.mentoring;

import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "대학생 답변 등록 응답")
public record CreateUniversityStudentAnswerResponse(
        @Schema(description = "대학생 답변 ID", example = "1")
        Long universityStudentAnswerId,

        @Schema(description = "대학생 답변 내용", example = "네, 가능합니다.")
        String answer
) {
    public static CreateUniversityStudentAnswerResponse from(UniversityStudentAnswer universityStudentAnswer) {
        return CreateUniversityStudentAnswerResponse.builder()
                .universityStudentAnswerId(universityStudentAnswer.getId())
                .answer(universityStudentAnswer.getAnswer())
                .build();
    }
}
