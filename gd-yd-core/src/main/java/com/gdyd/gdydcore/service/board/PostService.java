package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.repository.board.PostRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
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
public class PostService {
    private final PostRepository postRepository;

    public Page<Post> getPostListByPagination(Pageable pageable) {
        return postRepository.findAll(pageable);
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
    }

    public Post getPostByIdAndMemberId(Long postId, Long memberId) {
        return postRepository.findByIdAndMemberId(postId, memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.UNAUTHORIZED_MEMBER));
    }

    public List<Post> findByLikeCountGreaterThanEqualAndCreatedAtAfter(Long like, LocalDateTime weeksAgo, Pageable pageable) {
        return postRepository.findByLikeCountGreaterThanEqualAndCreatedAtAfter(like, weeksAgo, pageable);
    }

    public Page<Post> findByKeywordWithPriority(String keyword, Pageable pageable) {
        return postRepository.findByKeywordWithPriority(keyword, pageable);
    }

    @Transactional
    public void savePost(Post post)  {
        postRepository.save(post);
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
