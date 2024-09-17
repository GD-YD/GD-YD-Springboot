package com.gdyd.gdydapi.controller.board;

import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.request.board.UpdatePostRequest;
import com.gdyd.gdydapi.request.report.ReportRequest;
import com.gdyd.gdydapi.response.board.*;
import com.gdyd.gdydapi.response.common.LikeListResponse;
import com.gdyd.gdydapi.response.common.PageResponse;
import com.gdyd.gdydapi.response.common.ScrapListResponse;
import com.gdyd.gdydapi.response.common.ReportResponse;
import com.gdyd.gdydapi.service.board.PostCommandService;
import com.gdyd.gdydapi.service.board.PostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "Post 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/posts")
public class PostController {
    private final PostCommandService postCommandService;
    private final PostQueryService postQueryService;

    @Operation(summary = "Post 생성 API", description = "Post를 생성하는 API")
    @PostMapping
    public ResponseEntity<SavePostResponse> createPost(@RequestBody SavePostReqeust request) {
        SavePostResponse response = postCommandService.savePost(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 목록 조회 API", description = "Post의 목록을 조회하는 API")
    @Parameter(name = "page", description = "페이지 번호")
    @Parameter(name = "size", description = "페이지 크기")
    @GetMapping
    public ResponseEntity<PageResponse<GetPostResponse>> getPostList(
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "20") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        PageResponse<GetPostResponse> response = postQueryService.getPostList(pageable);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 상세 조회 API", description = "Post와 Comments를 PostId로 불러오는 API")
    @GetMapping("/{postId}")
    public ResponseEntity<GetPostResponse> getPostById(@PathVariable("postId") Long postId) {
        GetPostResponse response = postQueryService.getPostAndCommentsByPostId(postId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 수정 API", description = "Post를 수정하는 API")
    @PutMapping("/{postId}")
    public ResponseEntity<UpdatePostResponse> updatePost(@PathVariable("postId") Long postId, @RequestBody UpdatePostRequest request) {
        UpdatePostResponse response = postCommandService.updatePost(postId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 삭제 API", description = "Post를 삭제하는 API")
    @DeleteMapping("/{postId}")
    public ResponseEntity<DeletePostResponse> deletePost(@PathVariable("postId") Long postId) {
        DeletePostResponse response = postCommandService.deletePost(postId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 좋아요 API", description = "Post에 좋아요를 누르는 API")
    @Parameter(name = "postId", description = "Post ID", required = true)
    @PostMapping("/{postId}/like")
    public ResponseEntity<LikeListResponse> likePost(@PathVariable("postId") Long postId) {
        LikeListResponse response = postCommandService.likePost(postId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 좋아요 취소 API", description = "Post에 좋아요를 취소하는 API")
    @Parameter(name = "postId", description = "Post ID", required = true)
    @DeleteMapping("/{postId}/like")
    public ResponseEntity<LikeListResponse> unlikePost(@PathVariable("postId") Long postId) {
        LikeListResponse response = postCommandService.dislikePost(postId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 스크랩 API", description = "Post를 스크랩하는 API")
    @Parameter(name = "postId", description = "Post ID", required = true)
    @PostMapping("/{postId}/scrap")
    public ResponseEntity<ScrapListResponse> scrapPost(@PathVariable("postId") Long postId) {
        ScrapListResponse response = postCommandService.scrapPost(postId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 스크랩 취소 API", description = "Post를 스크랩 취소하는 API")
    @Parameter(name = "postId", description = "Post ID", required = true)
    @DeleteMapping("/{postId}/scrap")
    public ResponseEntity<ScrapListResponse> unscrapPost(@PathVariable("postId") Long postId) {
        ScrapListResponse response = postCommandService.unscrapPost(postId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 신고 API", description = "Post를 신고하는 API")
    @Parameter(name = "postId", description = "Post ID", required = true)
    @PostMapping("/{postId}/report")
    public ResponseEntity<ReportResponse> reportPost(@PathVariable("postId") Long postId, @RequestBody ReportRequest request) {
        ReportResponse response = postCommandService.reportPost(postId, request);
        return ResponseEntity.ok(response);
    }
}