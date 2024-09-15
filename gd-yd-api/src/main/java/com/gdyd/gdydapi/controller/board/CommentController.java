package com.gdyd.gdydapi.controller.board;

import com.gdyd.gdydapi.request.board.SaveCommentRequest;
import com.gdyd.gdydapi.request.board.UpdateCommentRequest;
import com.gdyd.gdydapi.request.report.ReportRequest;
import com.gdyd.gdydapi.response.board.SaveCommentResponse;
import com.gdyd.gdydapi.response.board.UpdateCommentResponse;
import com.gdyd.gdydapi.response.common.LikeListResponse;
import com.gdyd.gdydapi.response.common.ReportResponse;
import com.gdyd.gdydapi.service.board.CommentCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Comment", description = "Comment 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentCommandService commentCommandService;

    @Operation(summary = "Comment 생성 API", description = "Comment를 생성하는 API")
    @PostMapping
    public ResponseEntity<SaveCommentResponse> createComment(@RequestBody SaveCommentRequest request) {
        SaveCommentResponse response = commentCommandService.saveComment(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Comment 수정 API", description = "Comment를 수정하는 API")
    @PutMapping("/{commentId}")
    public ResponseEntity<UpdateCommentResponse> updateComment(@PathVariable("commentId") Long commentId, @RequestBody UpdateCommentRequest request) {
        UpdateCommentResponse response = commentCommandService.updateComment(commentId, request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Comment 삭제 API", description = "Comment를 삭제하는 API")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<HttpStatus> deleteComment(@PathVariable("commentId") Long commentId) {
        commentCommandService.deleteComment(commentId);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Comment 좋아요 API", description = "Comment에 좋아요를 누르는 API")
    @Parameter(name = "commentId", description = "Comment ID", required = true)
    @PostMapping("/{commentId}/like")
    public ResponseEntity<LikeListResponse> likeComment(@PathVariable("commentId") Long commentId) {
        LikeListResponse response = commentCommandService.likeComment(commentId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Comment 좋아요 취소 API", description = "Comment에 좋아요를 취소하는 API")
    @Parameter(name = "commentId", description = "Comment ID", required = true)
    @DeleteMapping("/{commentId}/like")
    public ResponseEntity<LikeListResponse> unlikeComment(@PathVariable("commentId") Long commentId) {
        LikeListResponse response = commentCommandService.dislikeComment(commentId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Comment 신고 API", description = "Comment를 신고하는 API")
    @Parameter(name = "commentId", description = "Comment ID", required = true)
    @PostMapping("/{commentId}/report")
    public ResponseEntity<ReportResponse> reportComment(@PathVariable("commentId") Long commentId, @RequestBody ReportRequest request) {
        ReportResponse response = commentCommandService.reportComment(commentId, request);
        return ResponseEntity.ok(response);
    }
}
