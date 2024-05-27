package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Schema(description = "Post 조회 응답")
public record GetPostResponse (
        @Schema(description = "Post ID", example = "1")
        Long id,

        @Schema(description = "Post 제목", example = "제 여친이 이상해요")
        String title,

        @Schema(description = "Post 내용", example = "자꾸 저를 무시해요")
        String content,

        @Schema(description = "Post 댓글", example = "댓글들")
        List<GetCommentResponse> comments
) {
        public static GetPostResponse from(Post post) {
            return GetPostResponse.builder()
                    .id(post.getId())
                    .title(post.getTitle())
                    .content(post.getContent())
                    .comments(post.getComments().stream()
                            .map(GetCommentResponse::from)
                            .collect(Collectors.toList()))
                    .build();
        }
}
