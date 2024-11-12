package com.gdyd.gdydapi.response.mentoring;

import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "대학생 답변 삭제 응답")
public record DeleteUniversityStudentAnswerResponse(
        @Schema(description = "대학생 답변 ID", example = "1")
        Long id
) {
    public static DeleteUniversityStudentAnswerResponse from(UniversityStudentAnswer universityStudentAnswer) {
        return DeleteUniversityStudentAnswerResponse.builder()
                .id(universityStudentAnswer.getId())
                .build();
    }
}
