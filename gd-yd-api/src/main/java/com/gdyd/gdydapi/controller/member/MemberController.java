package com.gdyd.gdydapi.controller.member;

import com.gdyd.gdydapi.request.member.UpdateProfileRequest;
import com.gdyd.gdydapi.response.board.GetPostSummaryResponse;
import com.gdyd.gdydapi.response.common.PageResponse;
import com.gdyd.gdydapi.response.member.ProfileResponse;
import com.gdyd.gdydapi.response.mentoring.HighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.service.member.MemberCommandService;
import com.gdyd.gdydapi.service.member.MemberQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Member", description = "Member 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberCommandService memberCommandService;
    private final MemberQueryService memberQueryService;

    @Operation(summary = "회원 이메일 중복 조회 API", description = "이메일 중복을 조회할 수 있는 API (true -> 중복된 이메일 존재)")
    @GetMapping("/existing-email")
    public ResponseEntity<Boolean> existingEmail(
            @RequestParam(value = "email") String email
    ) {
        boolean response = memberQueryService.existingEmail(email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 닉네임 중복 조회 API", description = "닉네임 중복을 조회할 수 있는 API (true -> 중복된 닉네임 존재)")
    @GetMapping("/existing-nickname")
    public ResponseEntity<Boolean> existingNickname(
            @RequestParam(value = "nickname") String nickname
    ) {
        boolean response = memberQueryService.existingNickname(nickname);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "프로필 조회 API", description = "현재 로그인한 사용자의 프로필 정보를 조회하는 API")
    @GetMapping("/profile")
    public ResponseEntity<ProfileResponse> getProfile() {
        ProfileResponse response = memberQueryService.getProfile();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "프로필 수정 API", description = "현재 로그인한 사용자의 프로필 정보를 수정하는 API")
    @PutMapping("/profile")
    public ResponseEntity<ProfileResponse> updateProfile(UpdateProfileRequest request) {
        ProfileResponse response = memberCommandService.updateProfile(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "스크랩한 게시글 목록 조회 API", description = "스크랩한 게시글 목록을 페이지네이션으로 조회하는 API")
    @Parameter(name = "page", description = "페이지 번호")
    @Parameter(name = "size", description = "페이지 크기")
    @GetMapping("/scrap-posts")
    public ResponseEntity<PageResponse<GetPostSummaryResponse>> getScrapPosts(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        PageResponse<GetPostSummaryResponse> response = memberQueryService.getScrapPosts(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "스크랩한 고등학생 질문 목록 조회 API", description = "스크랩한 고등학생 질문 목록을 페이지네이션으로 조회하는 API")
    @Parameter(name = "page", description = "페이지 번호")
    @Parameter(name = "size", description = "페이지 크기")
    @GetMapping("/scrap-high-school-student-questions")
    public ResponseEntity<PageResponse<HighSchoolStudentQuestionResponse>> getScrapHighSchoolStudentQuestions(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("id").descending());
        PageResponse<HighSchoolStudentQuestionResponse> response = memberQueryService.getScrapHighSchoolStudentQuestions(pageable);
        return ResponseEntity.ok(response);
    }
}
