package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydcore.domain.board.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Post 리스트용 요약 정보")
public record GetPostSummaryResponse(
        @Schema(description = "Post ID", example = "1")
        Long postId,

        @Schema(description = "Member Nickname", example = "1")
        String memberNickname,

        @Schema(description = "Post 제목", example = "제 여친이 이상해요")
        String title,

        @Schema(description = "Post 내용", example = "자꾸 저를 무시해요")
        String content,

        @Schema(description = "종아요 수", example = "4")
        Long likeCount,

        @Schema(description = "Post 댓글 수")
        Long comments
) {
        public static GetPostSummaryResponse from(Post post) {
                return GetPostSummaryResponse.builder()
                        .postId(post.getId())
                        .memberNickname(post.getMember().getNickname())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .likeCount(post.getLikeCount())
                        .comments((long)post.getComments().size())
                        .build();
        }
}
