package com.gdyd.gdydapi.request.board;

import com.gdyd.gdydcore.domain.board.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

public record UpdateCommentRequest(
        @Schema(description = "Comment 내용", example = "그럴땐 맛있는 걸 사 먹여 보세요!")
        String content
) {
        public static Comment toComment(SaveCommentRequest request) {
            return Comment.builder()
                    .content(request.content())
                    .build();
        }
}
