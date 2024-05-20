package com.gdyd.gdydapi.response.board;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Comment 삭제 응답")
public record DeleteCommentResponse(
        @Schema(description = "Comment ID", example = "1")
        Long id
) {
        public static DeleteCommentResponse from(Long id) {
            return DeleteCommentResponse.builder()
                    .id(id)
                    .build();
        }
}
