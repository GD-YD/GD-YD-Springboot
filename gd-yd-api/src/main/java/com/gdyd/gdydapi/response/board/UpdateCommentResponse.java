package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydcore.domain.board.Comment;
import com.gdyd.gdydcore.domain.board.Post;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Comment 수정 응답")
public record UpdateCommentResponse(
        @Schema(description = "Comment 수정 내용", example = "그럴땐 맛있는 걸 사 먹여 보세요!")
        String content
) {
        public static UpdateCommentResponse from(Comment comment) {
            return UpdateCommentResponse.builder()
                    .content(comment.getContent())
                    .build();
        }
}
