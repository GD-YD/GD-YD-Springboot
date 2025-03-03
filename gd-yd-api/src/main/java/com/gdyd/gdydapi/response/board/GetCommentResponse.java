package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydapi.response.common.BoardMemberResponse;
import com.gdyd.gdydcore.domain.board.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Comment 조회 응답")
public record GetCommentResponse(
        @Schema(description = "Comment ID", example = "1")
        Long commentId,

        @Schema(description = "Comment Content", example = "안녕하세요 반갑습니다")
        String content,

        @Schema(description = "Like Count", example = "3")
        Long likeCount,

        @Schema(description = "댓글 작성자")
        BoardMemberResponse member
) {
        public static GetCommentResponse from(Comment comment) {
                BoardMemberResponse memberResponse = BoardMemberResponse.from(comment.getMember());

                return GetCommentResponse.builder()
                        .commentId(comment.getId())
                        .content(comment.getContent())
                        .likeCount(comment.getLikeCount())
                        .member(memberResponse)
                        .build();
        }
}
