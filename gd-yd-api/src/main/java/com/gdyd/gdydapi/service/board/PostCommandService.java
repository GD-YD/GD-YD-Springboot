package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.request.board.SavePostReqeust;
import com.gdyd.gdydapi.response.board.SavePostResponse;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.service.board.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PostCommandService {

    private final PostService postService;

    public SavePostResponse savePost(SavePostReqeust request) {
        Post post = SavePostReqeust.toPost(request);
        postService.savePost(post);
        return SavePostResponse.from(post);
    }
}
