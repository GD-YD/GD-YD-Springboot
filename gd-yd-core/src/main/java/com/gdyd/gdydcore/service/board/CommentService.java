package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.repository.board.CommentRepository;
import com.gdyd.gdydcore.repository.board.PostRepository;
import com.gdyd.gdydcore.repository.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;
    private final MemberRepository memberRepository;
    private final PostRepository postRepository;
    @Transactional
    public void saveComment(Long memberId, Long postId, Comment comment)  {
        Member commentMember = memberRepository.findById(memberId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 멤버가 존재하지 않습니다."));
        Post commentPost = postRepository.findById(postId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 게시글이 존재하지 않습니다."));
        comment.setMember(commentMember);
        comment.setPost(commentPost);
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId, Comment comment) {
        Comment savedComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 댓글이 존재하지 않습니다."));

        savedComment.update(comment.getContent());
    }
}
