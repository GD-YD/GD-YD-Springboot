package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.repository.board.PostRepository;
import com.gdyd.gdydcore.repository.member.MemberRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;

    @Transactional
    public void savePost(Post post)  {
        postRepository.save(post);
    }

    @Transactional
    public Post getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        return post;
    }

    @Transactional
    public void updatePost(Long postId, Post post) {
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_POST));
        savedPost.update(post.getTitle(),
                post.getContent());
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
