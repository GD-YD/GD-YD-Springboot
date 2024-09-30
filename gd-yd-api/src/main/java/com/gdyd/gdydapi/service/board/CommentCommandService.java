package com.gdyd.gdydapi.service.board;

import com.gdyd.gdydapi.request.board.SaveCommentRequest;
import com.gdyd.gdydapi.request.board.UpdateCommentRequest;
import com.gdyd.gdydapi.request.report.ReportRequest;
import com.gdyd.gdydapi.response.board.SaveCommentResponse;
import com.gdyd.gdydapi.response.board.UpdateCommentResponse;
import com.gdyd.gdydapi.response.common.LikeListResponse;
import com.gdyd.gdydapi.response.common.ReportResponse;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.report.Report;
import com.gdyd.gdydcore.service.board.CommentService;
import com.gdyd.gdydcore.service.board.PostService;
import com.gdyd.gdydcore.service.member.LikeListService;
import com.gdyd.gdydcore.service.member.MemberService;
import com.gdyd.gdydcore.service.member.ReportService;
import com.gdyd.gdydsupport.ai.AIRequestGenerator;
import com.gdyd.gdydsupport.ai.ProfanityFilteringRequest;
import com.gdyd.gdydsupport.ai.ProfanityFilteringResponse;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import com.gdyd.gdydsupport.webhook.DiscordMessageGenerator;
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
    private final LikeListService likeListService;
    private final ReportService reportService;
    private final DiscordMessageGenerator discordMessageGenerator;
    private final AIRequestGenerator aiRequestGenerator;

    public SaveCommentResponse saveComment(SaveCommentRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        Post post = postService.getPostById(request.postId());

        ProfanityFilteringRequest aiFilteringRequest = ProfanityFilteringRequest.from(request.content());
        ProfanityFilteringResponse aiFilteringResponse = aiRequestGenerator.sendAbuseFilteringRequest(aiFilteringRequest);
        if (Boolean.TRUE.equals(aiFilteringResponse.isProfanityDetected())) {
            throw new BusinessException(ErrorCode.CONTAINS_PROFANITY);
        }

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

    /**
     * 댓글 좋아요
     */
    public LikeListResponse likeComment(Long commentId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        Comment comment = commentService.getCommentById(commentId);
        if (likeListService.existsByMemberIdAndCommentId(memberId, commentId)) {
            throw new BusinessException(ErrorCode.ALREADY_LIKED);
        }
        LikeList likeList = LikeList.commentLikeBuilder()
                .member(member)
                .comment(comment)
                .commentLikeBuild();
        likeListService.save(likeList);
        comment.increaseLikeCount();
        return LikeListResponse.from(likeList);
    }

    public LikeListResponse dislikeComment(Long commentId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        Comment comment = commentService.getCommentById(commentId);
        if (!likeListService.existsByMemberIdAndCommentId(memberId, commentId)) {
            throw new BusinessException(ErrorCode.ALREADY_UNLIKED);
        }

        comment.decreaseLikeCount();
        LikeList likeList = member.getLikeLists().stream()
                .filter(like -> like.getComment().getId().equals(commentId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.ALREADY_UNLIKED));
        likeListService.delete(likeList);
        return LikeListResponse.from(likeList);
    }

    public ReportResponse reportComment(Long commentId, ReportRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member reporter = memberService.getMemberById(memberId);
        Comment comment = commentService.getCommentById(commentId);

        if (reportService.existsByMemberIdAndCommentId(memberId, commentId)) {
            throw new BusinessException(ErrorCode.ALREADY_REPORTED);
        }

        comment.increaseReportCount();
        Report report = Report.commentReportBuilder()
                .reporter(reporter)
                .comment(comment)
                .content(request.content())
                .commentReportBuild();
        reportService.save(report);
        String message = discordMessageGenerator.commentReportMessage(
                reporter.getEmail(),
                commentId,
                comment.getContent(),
                request.content());
        discordMessageGenerator.sendReportMessage(message);
        return ReportResponse.from(report);
    }
}
