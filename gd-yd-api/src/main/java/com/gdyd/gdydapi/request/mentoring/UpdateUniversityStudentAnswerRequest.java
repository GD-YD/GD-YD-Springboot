package com.gdyd.gdydapi.request.mentoring;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "대학생 답변 수정 요청")
public record UpdateUniversityStudentAnswerRequest(
        @NotBlank(message = "대학생 답변 내용은 필수입니다.")
        @Schema(description = "대학생 답변 내용", example = "네, 가능합니다.")
        String answer
) {
}
