package com.gdyd.gdydapi.response.mentoring;

import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "대학생 답변 응답")
public record UniversityStudentAnswerResponse(
        @Schema(description = "대학생 답변 ID", example = "1")
        Long id,

        @Schema(description = "대학생 닉네임", example = "똑똑한 대학생3")
        String universityStudentNickname,

        @Schema(description = "대학생 답변 내용", example = "이 성적으로는 단국대가 적합할 것 같습니다.")
        String answer,

        @Schema(description = "대학생 답변에 대한 좋아요 수", example = "3")
        Long likeCount,

        @Schema(description = "대학생 답변 등록일", example = "2021-10-01T00:00:00")
        String createdAt
) {
    public static UniversityStudentAnswerResponse from(UniversityStudentAnswer universityStudentAnswer) {
        return UniversityStudentAnswerResponse.builder()
                .id(universityStudentAnswer.getId())
                .universityStudentNickname(universityStudentAnswer.getUniversityStudent().getNickname())
                .answer(universityStudentAnswer.getAnswer())
                .likeCount(universityStudentAnswer.getLikeCount())
                .createdAt(universityStudentAnswer.getCreatedAt().toString())
                .build();
    }
}
