package com.gdyd.gdydapi.request.board;

import com.gdyd.gdydcore.domain.board.Post;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Post 생성 요청")
public record SavePostReqeust(
        @Schema(description = "Post 제목", example = "제 여친이 이상해요")
        String title,

        @Schema(description = "Post 내용", example = "자꾸 저를 무시해요")
        String content
) {
        public static Post toPost(SavePostReqeust request) {
                return Post.builder()
                        .title(request.title())
                        .content(request.content())
                        .build();
        }
}
