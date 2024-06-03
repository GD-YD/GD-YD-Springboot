package com.gdyd.gdydapi.controller.board;

import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.request.board.UpdatePostRequest;
import com.gdyd.gdydapi.response.board.*;
import com.gdyd.gdydapi.service.board.PostCommandService;
import com.gdyd.gdydapi.service.board.PostQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
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
    @GetMapping
    public ResponseEntity<GetAllPostResponse> getAllPost() {
        GetAllPostResponse response = postQueryService.getAllPost();
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

}