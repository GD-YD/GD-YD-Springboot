package com.gdyd.gdydapi.controller.board;

import com.gdyd.gdydcore.dto.board.PostDto;
import com.gdyd.gdydcore.service.board.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class PostController {

    @Autowired
    private final PostService postService;

    @PostMapping("/post")
    public String createPost(@RequestBody PostDto postDto) throws IOException {
        postService.postWrite(postDto);

        return "";
    }
}