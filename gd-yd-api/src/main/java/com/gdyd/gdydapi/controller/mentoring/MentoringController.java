package com.gdyd.gdydapi.controller.mentoring;

import com.gdyd.gdydapi.request.mentoring.CreateHighSchoolStudentQuestionRequest;
import com.gdyd.gdydapi.request.mentoring.CreateUniversityStudentAnswerRequest;
import com.gdyd.gdydapi.request.report.ReportRequest;
import com.gdyd.gdydapi.response.common.LikeListResponse;
import com.gdyd.gdydapi.response.common.PageResponse;
import com.gdyd.gdydapi.response.common.ReportResponse;
import com.gdyd.gdydapi.response.mentoring.CreateHighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.response.mentoring.CreateUniversityStudentAnswerResponse;
import com.gdyd.gdydapi.response.mentoring.DetailHighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.response.mentoring.HighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.service.mentoring.MentoringCommandService;
import com.gdyd.gdydapi.service.mentoring.MentoringQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Mentoring", description = "Mentoring 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/mentoring")
public class MentoringController {
    private final MentoringCommandService mentoringCommandService;
    private final MentoringQueryService mentoringQueryService;

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

    @Operation(summary = "고등학생 질문 목록 조회 API", description = "고등학생 질문 목록을 페이지네이션으로 조회하는 API")
    @Parameter(name = "page", description = "페이지 번호")
    @Parameter(name = "size", description = "페이지 크기")
    @GetMapping("/high-school-student-questions")
    public ResponseEntity<PageResponse<HighSchoolStudentQuestionResponse>> getHighSchoolStudentQuestionList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<HighSchoolStudentQuestionResponse> response = mentoringQueryService.getHighSchoolStudentQuestions(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "고등학생 질문 상세 조회 API", description = "고등학생 질문 상세 정보(대학생 답변 내용 등)를 조회하는 API")
    @Parameter(name = "highSchoolStudentQuestionId", description = "고등학생 질문글 ID", required = true)
    @GetMapping("/high-school-student-question/{highSchoolStudentQuestionId}")
    public ResponseEntity<DetailHighSchoolStudentQuestionResponse> getDetailHighShcoolStudentQuestion(
            @PathVariable("highSchoolStudentQuestionId") Long highSchoolStudentQuestionId
    ) {
        DetailHighSchoolStudentQuestionResponse response = mentoringQueryService.getHighSchoolStudentQuestionDetail(highSchoolStudentQuestionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "고등학생 질문 좋아요 API", description = "고등학생 질문글에 좋아요를 증가하는 API (이미 해당 회원이 좋아요를 한 경우 예외 처리)")
    @Parameter(name = "highSchoolStudentQuestionId", description = "고등학생 질문글 ID", required = true)
    @PostMapping("/high-school-student-question/{highSchoolStudentQuestionId}/like")
    public ResponseEntity<LikeListResponse> likeHighSchoolStudentQuestionLike(
            @PathVariable("highSchoolStudentQuestionId") Long highSchoolStudentQuestionId
    ) {
        LikeListResponse response = mentoringCommandService.likeHighSchoolStudentQuestion(highSchoolStudentQuestionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "고등학생 질문 좋아요 취소 API", description = "고등학생 질문글에 좋아요를 감소하는 API (이미 해당 회원이 좋아요를 취소한 경우 예외 처리)")
    @Parameter(name = "highSchoolStudentQuestionId", description = "고등학생 질문글 ID", required = true)
    @DeleteMapping("/high-school-student-question/{highSchoolStudentQuestionId}/like")
    public ResponseEntity<LikeListResponse> dislikeHighSchoolStudentQuestion(
            @PathVariable("highSchoolStudentQuestionId") Long highSchoolStudentQuestionId
    ) {
        LikeListResponse response = mentoringCommandService.dislikeHighSchoolStudentQuestion(highSchoolStudentQuestionId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "대학생 답변 좋아요 API", description = "대학생 답변에 좋아요를 증가하는 API (이미 해당 회원이 좋아요를 한 경우 예외 처리)")
    @Parameter(name = "universityStudentAnswerId", description = "대학생 답변글 ID", required = true)
    @PostMapping("/university-student-answer/{universityStudentAnswerId}/like")
    public ResponseEntity<LikeListResponse> likeUniversityStudentAnswer(
            @PathVariable("universityStudentAnswerId") Long universityStudentAnswerId
    ) {
        LikeListResponse response = mentoringCommandService.likeUniversityStudentAnswer(universityStudentAnswerId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "대학생 답변 좋아요 취소 API", description = "대학생 답변에 좋아요를 감소하는 API (이미 해당 회원이 좋아요를 취소한 경우 예외 처리)")
    @Parameter(name = "universityStudentAnswerId", description = "대학생 답변글 ID", required = true)
    @DeleteMapping("/university-student-answer/{universityStudentAnswerId}/like")
    public ResponseEntity<LikeListResponse> dislikeUniversityStudentAnswer(
            @PathVariable("universityStudentAnswerId") Long universityStudentAnswerId
    ) {
        LikeListResponse response = mentoringCommandService.dislikeUniversityStudentAnswer(universityStudentAnswerId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "고등학생 질문 신고 API", description = "고등학생 질문을 신고하는 API")
    @Parameter(name = "highSchoolStudentQuestionId", description = "고등학생 질문글 ID", required = true)
    @PostMapping("/high-school-student-question/{highSchoolStudentQuestionId}/report")
    public ResponseEntity<ReportResponse> reportHighSchoolStudentQuestion(@PathVariable("highSchoolStudentQuestionId") Long highSchoolStudentQuestionId, @RequestBody ReportRequest request) {
        ReportResponse response = mentoringCommandService.reportHighSchoolStudentQuestion(highSchoolStudentQuestionId, request);
        return ResponseEntity.ok(response);
    }
}
