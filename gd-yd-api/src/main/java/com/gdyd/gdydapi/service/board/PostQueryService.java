package com.gdyd.gdydapi.service.board;

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

    public PageResponse<GetPostSummaryResponse> searchPostListByKeyword(String keyword, Pageable pageable) {
        Page<Post> postPage = postService.findByKeywordWithPriority(keyword, pageable);
        return PageResponse.of(postPage.getContent().stream().map(GetPostSummaryResponse::from).toList());
    }

    public GetPostResponse getPostAndCommentsByPostId(Long postId) {
        Post post = postService.getPostById(postId);
        return GetPostResponse.from(post);
    }

    public PageResponse<GetPostSummaryResponse> getBestPostList(Long like, LocalDateTime weeksAgo, Pageable pageable) {
        List<Post> posts = postService.findByLikeCountGreaterThanEqualAndCreatedAtAfter(like, weeksAgo, pageable);
        return PageResponse.of(posts.stream().map(GetPostSummaryResponse::from).toList());
    }
}
