package com.gdyd.gdydapi.controller.board;

import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.response.board.SavePostResponse;
import com.gdyd.gdydapi.service.board.PostCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}