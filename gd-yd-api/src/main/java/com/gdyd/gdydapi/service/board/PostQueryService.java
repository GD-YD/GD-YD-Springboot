package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.response.board.GetBestPostResponse;
import com.gdyd.gdydapi.response.board.GetPostResponse;
import com.gdyd.gdydapi.response.board.GetPostSummaryResponse;
import com.gdyd.gdydapi.response.common.PageResponse;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.service.board.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostQueryService {
    private final PostService postService;

    public PageResponse<GetPostSummaryResponse> getPostList(Pageable pageable) {
        Page<Post> pages = postService.getPostListByPagination(pageable);
        return PageResponse.of(pages.getContent().stream().map(GetPostSummaryResponse::from).toList());
    }

    public GetPostResponse getPostAndCommentsByPostId(Long postId) {
        Post post = postService.getPostById(postId);
        return GetPostResponse.from(post);
    }

    public GetBestPostResponse getBestPost(LocalDateTime weekAgo) {
        List<Post> posts = postService.findTop20ByCreatedAtIsAfterOrderByLikeCountDesc(weekAgo);
        return GetBestPostResponse.from(posts);
    }
}
