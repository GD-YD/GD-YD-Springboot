package com.gdyd.gdydapi.response.board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Post 삭제 응답")
public record DeletePostResponse (
        @Schema(description = "Post ID", example = "1")
        Long id
) {
        public static DeletePostResponse from(Long id) {
            return DeletePostResponse.builder()
                    .id(id)
                    .build();
        }
}
