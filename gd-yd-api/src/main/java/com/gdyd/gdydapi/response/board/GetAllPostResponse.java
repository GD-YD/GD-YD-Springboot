package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydcore.domain.board.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;
import java.util.stream.Collectors;

@Builder
@Schema(description = "Post 목록 조회 응답")
public record GetAllPostResponse(
        @Schema(description = "Post 리스트")
        List<GetPostSummaryResponse> posts
) {
        public static GetAllPostResponse from(List<Post> posts) {
            return GetAllPostResponse.builder()
                    .posts(posts.stream()
                            .map(GetPostSummaryResponse::from)
                            .collect(Collectors.toList()))
                    .build();
        }
}
