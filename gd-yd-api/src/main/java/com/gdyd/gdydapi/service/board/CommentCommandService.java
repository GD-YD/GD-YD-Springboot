package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.request.board.SaveCommentRequest;
import com.gdyd.gdydapi.request.board.UpdateCommentRequest;
import com.gdyd.gdydapi.response.board.DeleteCommentResponse;
import com.gdyd.gdydapi.response.board.SaveCommentResponse;
import com.gdyd.gdydapi.response.board.UpdateCommentResponse;
import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.service.board.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {
    private final CommentService commentService;

    public SaveCommentResponse saveComment(SaveCommentRequest request) {
        Comment comment = SaveCommentRequest.toComment(request);
        commentService.saveComment(request.memberId(), request.postId(), comment);
        return SaveCommentResponse.from(comment);
    }

    public UpdateCommentResponse updateComment(Long commentId, UpdateCommentRequest request) {
        Comment comment = UpdateCommentRequest.toComment(request);
        commentService.updateComment(commentId, comment);
        return UpdateCommentResponse.from(comment);
    }

    public DeleteCommentResponse deleteComment(Long commentId) {
        commentService.deleteComment(commentId);
        return DeleteCommentResponse.from(commentId);
    }
}
