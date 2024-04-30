package com.gdyd.gdydapi.controller.board;

import com.gdyd.gdydcore.service.board;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/board")
public class PostController {


    private final UniversityStudentService universityStudentService;
    @Autowired
    private final PostService postService;

    @PostMapping("/post")
    public String createPost(@RequestBody PostDto postDto) throws IOException {
        postService.postWrite(postDto);

        return "";
    }
}