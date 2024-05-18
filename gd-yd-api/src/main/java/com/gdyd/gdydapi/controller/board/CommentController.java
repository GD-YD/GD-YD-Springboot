package com.gdyd.gdydapi.controller.board;

import com.gdyd.gdydapi.request.board.SaveCommentRequest;
import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.response.board.SaveCommentResponse;
import com.gdyd.gdydapi.response.board.SavePostResponse;
import com.gdyd.gdydapi.service.board.CommentCommandService;
import com.gdyd.gdydapi.service.board.PostCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment", description = "Comment 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/comment")
public class CommentController {
    private final CommentCommandService commentCommandService;

    @Operation(summary = "Comment 생성 API", description = "Comment를 생성하는 API")
    @PostMapping
    public ResponseEntity<SaveCommentResponse> createComment(@RequestBody SaveCommentRequest request) {
        SaveCommentResponse response = commentCommandService.savePost(request);
        return ResponseEntity.ok(response);
    }
}
