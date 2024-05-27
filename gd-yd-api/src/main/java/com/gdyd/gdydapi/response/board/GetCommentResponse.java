package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydapi.response.member.MemberResponse;
import com.gdyd.gdydapi.response.member.ProfileResponse;
import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Column;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Builder;
import org.hibernate.annotations.ColumnDefault;

@Builder
@Schema(description = "Comment 조회 응답")
public record GetCommentResponse(
        @Schema(description = "Comment Content", example = "안녕하세요 반갑습니다")
        String content,

        @Schema(description = "Like Count", example = "3")
        Long likeCount,

        @Schema(description = "댓글 작성자", example = "")
        MemberResponse member,

        @Schema(description = "Post ID", example = "1")
        Long postId
) {
        public static GetCommentResponse from(Comment comment) {
                return GetCommentResponse.builder()
                        .content(comment.getContent())
                        .likeCount(comment.getLikeCount())
                        .member(MemberResponse.from(comment.getMember()))
                        .build();
        }
}
