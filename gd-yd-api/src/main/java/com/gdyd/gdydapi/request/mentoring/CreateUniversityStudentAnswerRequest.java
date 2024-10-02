package com.gdyd.gdydapi.request.mentoring;

import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "대학생 답변 등록 요청")
public record CreateUniversityStudentAnswerRequest(
        @NotBlank(message = "대학생 답변 내용은 필수입니다.")
        @Schema(description = "대학생 답변 내용", example = "네, 가능합니다.")
        String answer
) {
    public static UniversityStudentAnswer toUniversityStudentAnswer(
            CreateUniversityStudentAnswerRequest request,
            UniversityStudent universityStudent,
            HighSchoolStudentQuestion highSchoolStudentQuestion
    ) {
        return UniversityStudentAnswer.builder()
                .answer(request.answer())
                .universityStudent(universityStudent)
                .highSchoolStudentQuestion(highSchoolStudentQuestion)
                .build();
    }
}
