package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Schema(description = "Post 조회 응답")
public record GetPostResponse (
        @Schema(description = "Post ID", example = "1")
        Long postId,

        @Schema(description = "Member ID", example = "1")
        Long memberId,

        @Schema(description = "Post 제목", example = "제 여친이 이상해요")
        String title,

        @Schema(description = "Post 내용", example = "자꾸 저를 무시해요")
        String content,

        @Schema(description = "종아요 수", example = "4")
        Long likeCount,

        @Schema(description = "생성 시간", example = "2024-05-30 15:27:38.999973")
        String createdAt,

        @Schema(description = "최종 수정 시간", example = "2024-05-30 18:27:38.999973")
        String updatedAt,

        @Schema(description = "Post 댓글")
        List<GetCommentResponse> comments
) {
        public static GetPostResponse from(Post post) {
            return GetPostResponse.builder()
                    .postId(post.getId())
                    .memberId(post.getMember().getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .likeCount(post.getLikeCount())
                    .createdAt(post.getCreatedAt().toString())
                    .updatedAt(post.getUpdatedAt().toString())
                    .comments(post.getComments().stream()
                            .map(GetCommentResponse::from)
                            .collect(Collectors.toList()))
                    .build();
        }
}
