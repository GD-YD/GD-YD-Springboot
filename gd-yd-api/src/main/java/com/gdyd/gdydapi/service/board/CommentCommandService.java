package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.request.board.SaveCommentRequest;
import com.gdyd.gdydapi.request.board.UpdateCommentRequest;
import com.gdyd.gdydapi.response.board.SaveCommentResponse;
import com.gdyd.gdydapi.response.board.UpdateCommentResponse;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.service.board.CommentService;
import com.gdyd.gdydcore.service.board.PostService;
import com.gdyd.gdydcore.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class CommentCommandService {
    private final CommentService commentService;
    private final MemberService memberService;
    private final PostService postService;

    public SaveCommentResponse saveComment(SaveCommentRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        Post post = postService.getPostById(request.postId());

        Comment comment = SaveCommentRequest.toComment(request);
        comment.updateMember(member);
        comment.updatePost(post);
        commentService.saveComment(comment);
        return SaveCommentResponse.from(comment);
    }

    public UpdateCommentResponse updateComment(Long commentId, UpdateCommentRequest request) {
        Comment comment = UpdateCommentRequest.toComment(request);
        commentService.updateComment(commentId, comment);
        return UpdateCommentResponse.from(comment);
    }

    public void deleteComment(Long commentId) {
        commentService.deleteComment(commentId);
    }
}
