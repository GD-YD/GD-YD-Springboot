package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydcore.domain.board.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "인기 Post 목록 조회 응답")
public record GetBestPostResponse(
        @Schema(description = "Post 리스트")
        List<GetPostSummaryResponse> posts
) {
        public static GetBestPostResponse from(List<Post> posts) {
            return GetBestPostResponse.builder()
                    .posts(posts.stream().map(GetPostSummaryResponse::from).toList())
                    .build();
        }
}
