package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.repository.board.PostRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void updatePost(Long postId, Post post) {
        Post savedPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 게시글이 존재하지 않습니다."));

        savedPost.update(post.getTitle(),
                post.getContent());
    }

    @Transactional
    public void deletePost(Long postId) {
        postRepository.deleteById(postId);
    }
}
