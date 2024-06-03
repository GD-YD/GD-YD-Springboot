package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.response.board.GetAllPostResponse;
import com.gdyd.gdydapi.response.board.GetPostResponse;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.service.board.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {
    private final PostService postService;

    public GetAllPostResponse getAllPost() {
        List<Post> posts = postService.getAllPost();
        return GetAllPostResponse.from(posts);
    }

    public GetPostResponse getPostAndCommentsByPostId(Long postId) {
        Post post = postService.getPostById(postId);
        return GetPostResponse.from(post);
    }
}
