package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.repository.board.PostRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void savePost(Post post)  {
        postRepository.save(post);
    }

    public List<Post> getAllPost() {
        return postRepository.findAll();
    }

    public Post getPostById(Long postId) {
        return postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
    }

    public Post getPostByIdAndMemberId(Long postId, Long memberId) {
        return postRepository.findByIdAndMemberId(postId, memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.UNAUTHORIZED_MEMBER));
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
