package com.gdyd.gdydapi.response.mentoring;

import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestionMedia;
import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "고등학생 질문 상세 응답")
public record DetailHighSchoolStudentQuestionResponse(
        @Schema(description = "고등학생 질문 ID", example = "1")
        Long id,

        @Schema(description = "고등학생 닉네임", example = "궁금한 고등학생2")
        String highSchoolStudentNickname,

        @Schema(description = "고등학생 질문 제목", example = "이 성적으로 어떤 대학을 갈수 있을까요?")
        String title,

        @Schema(description = "고등학생 질문 내용", example = "이번에 모의고사 성적이 나왔는데, 이 성적으로 어떤 대학을 갈수 있을까요?")
        String question,

        @Schema(description = "고등학생 질문에서 답변을 원하는 대학생의 학교명", example = "단국대학교")
        String universityNameTag,

        @Schema(description = "고등학생 질문에서 답변을 원하는 대학생의 학과", example = "컴퓨터/SW")
        String universityMajorTag,

        @Schema(description = "고등학생 질문에서 답변을 원하는 대학생의 학년", example = "1학년")
        String universityGradeTag,

        @Schema(description = "고등학생 질문에 대한 좋아요 수", example = "3")
        Long likeCount,

        @Schema(description = "고등학생 질문에 대한 대학생 답변 수", example = "7")
        Long awnserCount,

        @Schema(description = "고등학생 질문 미디어 URL")
        List<String> highSchoolStudentQuestionMediaUrls,

        @Schema(description = "대학생 답변 목록")
        List<UniversityStudentAnswerResponse> answers,

        @Schema(description = "고등학생 질문 등록일", example = "2021-10-01T00:00:00")
        String createdAt
) {
    public static DetailHighSchoolStudentQuestionResponse of(
            HighSchoolStudentQuestion highSchoolStudentQuestion,
            List<UniversityStudentAnswer> universityStudentAnswers
    ) {
        List<String> highSchoolStudentQuestionMediaUrls = highSchoolStudentQuestion.getHighSchoolStudentQuestionMedias().stream()
                .map(HighSchoolStudentQuestionMedia::getUrl)
                .toList();

        return DetailHighSchoolStudentQuestionResponse.builder()
                .id(highSchoolStudentQuestion.getId())
                .highSchoolStudentNickname(highSchoolStudentQuestion.getHighSchoolStudent().getNickname())
                .title(highSchoolStudentQuestion.getTitle())
                .question(highSchoolStudentQuestion.getQuestion())
                .universityNameTag(highSchoolStudentQuestion.getUniversityNameTag())
                .universityMajorTag(highSchoolStudentQuestion.getUniversityMajorTag().getValue())
                .universityGradeTag(highSchoolStudentQuestion.getUniversityGradeTag().getValue())
                .likeCount(highSchoolStudentQuestion.getLikeCount())
                .awnserCount(highSchoolStudentQuestion.getAnswerCount())
                .highSchoolStudentQuestionMediaUrls(highSchoolStudentQuestionMediaUrls)
                .answers(universityStudentAnswers.stream().map(UniversityStudentAnswerResponse::from).toList())
                .createdAt(highSchoolStudentQuestion.getCreatedAt().toString())
                .build();
    }
}
