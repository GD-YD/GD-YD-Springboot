package com.gdyd.gdydapi.controller.board;

import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.response.board.DeletePostResponse;
import com.gdyd.gdydapi.response.board.SavePostResponse;
import com.gdyd.gdydapi.service.board.PostCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Post", description = "Post 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/post")
public class PostController {

    private final PostCommandService postCommandService;

    @Operation(summary = "Post 생성 API", description = "Post를 생성하는 API")
    @PostMapping
    public ResponseEntity<SavePostResponse> createPost(@RequestBody SavePostReqeust request) {
        SavePostResponse response = postCommandService.savePost(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Post 삭제 API", description = "Post를 삭제하는 API")
    @DeleteMapping("/{id}")
    public ResponseEntity<DeletePostResponse> deletePost(@PathVariable("id") Long id) {
        DeletePostResponse response = postCommandService.deletePost(id);
        return ResponseEntity.ok(response);
    }

}