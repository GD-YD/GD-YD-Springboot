package com.gdyd.gdydapi.request.mentoring;

import com.gdyd.gdydcore.domain.member.Grade;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.UniversityMajorCategory;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestionMedia;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

import java.util.List;

@Schema(description = "고등학생 질문 등록 요청")
public record CreateHighSchoolStudentQuestionRequest(
        @NotBlank(message = "질문글의 제목은 필수입니다.")
        @Schema(description = "질문글 제목", example = "이 정도 등급으로 입학할 수 있을까요?")
        String title,

        @NotBlank(message = "질문글의 내용은 필수입니다.")
        @Schema(description = "질문글 내용", example = "12344로 해당 대학에 입학할 수 있을까요?")
        String question,

        @Schema(description = "질문글에서 답변을 원하는 대학생의 학교명")
        String universityNameTag,

        @Schema(description = "질문글에서 답변을 원하는 대학생의 학과")
        UniversityMajorCategory universityMajorTag,

        @Schema(description = "질문글에서 답변을 원하는 대학생의 학년")
        Grade universityGradeTag,

        @Schema(description = "고등학생 질문 미디어 URL")
        List<String> highSchoolStudentQuestionMediaUrls
) {
    public static HighSchoolStudentQuestion toHighSchoolStudentQuestion(
            CreateHighSchoolStudentQuestionRequest request,
            HighSchoolStudent highSchoolStudent
    ) {
        UniversityMajorCategory universityMajorTag = request.universityMajorTag() == null
                ? UniversityMajorCategory.DEFAULT
                : request.universityMajorTag();
        Grade universityGradeTag = request.universityGradeTag() == null
                ? Grade.DEFAULT
                : request.universityGradeTag();

        return HighSchoolStudentQuestion.builder()
                .title(request.title())
                .question(request.question())
                .highSchoolStudent(highSchoolStudent)
                .universityNameTag(request.universityNameTag())
                .universityGradeTag(universityGradeTag)
                .universityMajorTag(universityMajorTag)
                .build();
    }

    public static List<HighSchoolStudentQuestionMedia> toHighSchoolStudentQuestionMedia(
            CreateHighSchoolStudentQuestionRequest request,
            HighSchoolStudentQuestion highSchoolStudentQuestion
    ) {
        return request.highSchoolStudentQuestionMediaUrls().stream()
                .map(url -> HighSchoolStudentQuestionMedia.builder()
                        .url(url)
                        .highSchoolStudentQuestion(highSchoolStudentQuestion)
                        .build())
                .toList();
    }
}
