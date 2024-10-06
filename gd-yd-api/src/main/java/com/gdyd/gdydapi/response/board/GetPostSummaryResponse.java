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
        Long comments,

        @Schema(description = "Post 썸네일 URL", example = "https://gdyd.s3.ap-northeast-2.amazonaws.com/...")
        String thumbnailUrl
) {
        public static GetPostSummaryResponse from(Post post) {
                String thumbnailUrl = post.getPostMedias().isEmpty() ? "DEFAULT" : post.getPostMedias().get(0).getUrl();
                return GetPostSummaryResponse.builder()
                        .postId(post.getId())
                        .memberNickname(post.getMember().getNickname())
                        .title(post.getTitle())
                        .content(post.getContent())
                        .likeCount(post.getLikeCount())
                        .comments((long)post.getComments().size())
                        .thumbnailUrl(thumbnailUrl)
                        .build();
        }
}
