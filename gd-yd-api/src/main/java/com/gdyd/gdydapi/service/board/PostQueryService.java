package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.response.board.GetPostResponse;
import com.gdyd.gdydapi.response.common.PageResponse;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.service.board.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {
    private final PostService postService;

    public PageResponse<GetPostResponse> getPostList(Pageable pageble) {
        Page<Post> pages = postService.getPostListByPagination(pageble);
        return PageResponse.of(pages.getContent().stream().map(GetPostResponse::from).toList());
    }

    public GetPostResponse getPostAndCommentsByPostId(Long postId) {
        Post post = postService.getPostById(postId);
        return GetPostResponse.from(post);
    }
}
