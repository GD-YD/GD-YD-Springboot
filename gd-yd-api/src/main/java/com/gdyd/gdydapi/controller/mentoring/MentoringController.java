package com.gdyd.gdydapi.controller.mentoring;

import com.gdyd.gdydapi.request.mentoring.CreateHighSchoolStudentQuestionRequest;
import com.gdyd.gdydapi.request.mentoring.CreateUniversityStudentAnswerRequest;
import com.gdyd.gdydapi.response.mentoring.CreateHighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.response.mentoring.CreateUniversityStudentAnswerResponse;
import com.gdyd.gdydapi.service.mentoring.MentoringCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mentoring", description = "Mentoring 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentoring")
public class MentoringController {
    private final MentoringCommandService mentoringCommandService;

    @Operation(summary = "고등학생 질문 등록 API", description = "고등학생이 질문을 등록하는 API")
    @PostMapping("/high-school-student-question")
    public ResponseEntity<CreateHighSchoolStudentQuestionResponse> createHighSchoolStudentQuestion(
            @RequestBody @Valid CreateHighSchoolStudentQuestionRequest request
    ) {
        CreateHighSchoolStudentQuestionResponse response = mentoringCommandService.createHighSchoolStudentQuestion(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "대학생 답변 등록 API", description = "대학생이 답변을 등록하는 API")
    @Parameter(name = "highSchoolStudentQuestionId", description = "고등학생 질문글 ID", required = true)
    @PostMapping("/{highSchoolStudentQuestionId}/university-student-answer")
    public ResponseEntity<CreateUniversityStudentAnswerResponse> createUniversityStudentAnswer(
            @PathVariable("highSchoolStudentQuestionId") Long highSchoolStudentQuestionId,
            @RequestBody @Valid CreateUniversityStudentAnswerRequest request
    ) {
        CreateUniversityStudentAnswerResponse response = mentoringCommandService.createUniversityStudentAnswer(highSchoolStudentQuestionId, request);
        return ResponseEntity.ok(response);
    }
}