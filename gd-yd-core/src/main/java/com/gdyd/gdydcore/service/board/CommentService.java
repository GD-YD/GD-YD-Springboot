package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.repository.board.CommentRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class CommentService {
    private final CommentRepository commentRepository;

    public Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_COMMENT));
    }

    @Transactional
    public void saveComment(Comment comment)  {
        commentRepository.save(comment);
    }

    @Transactional
    public void updateComment(Long commentId, Comment comment) {
        Comment savedComment = commentRepository.findById(commentId)
                .orElseThrow(() -> new IllegalArgumentException("해당 id를 가진 댓글이 존재하지 않습니다."));

        savedComment.update(comment.getContent());
    }

    @Transactional
    public void deleteComment(Long commentId) {
        commentRepository.deleteById(commentId);
    }
}
