package com.gdyd.gdydapi.controller.board;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class PostController {


    private final UniversityStudentService universityStudentService;

    @PostMapping("/post")
    public String createPost(@RequestBody PostRequestDto requestDto) throws IOException {
        // 요청으로부터 게시글 정보를 추출하여 새로운 Post 객체를 생성합니다.
        Post post = Post.builder()
                .title(requestDto.getTitle())
                .content(requestDto.getContent())
                .build();

        // 게시글을 레포지토리에 저장합니다.
        universityStudentService.savePost(post);

        return "";
    }
}