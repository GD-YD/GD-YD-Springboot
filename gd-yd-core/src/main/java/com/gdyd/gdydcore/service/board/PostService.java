package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.repository.board.PostRepository;
import com.gdyd.gdydcore.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    private final PostRepository postRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void savePost(Long memberId, Post post)  {
        Member postMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 멤버가 존재하지 않습니다."));;
        post.setMember(postMember);
        postRepository.save(post);
    }

    public Post getPostById(Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 게시글이 존재하지 않습니다."));
        return post;
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
