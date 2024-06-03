package com.gdyd.gdydapi.response.board;

import com.gdyd.gdydcore.domain.board.Comment;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "Comment 생성 응답")
public record SaveCommentResponse(
        @Schema(description = "Comment ID", example = "1")
        Long id,

        @Schema(description = "Comment 내용", example = "그럴땐 맛있는 걸 사 먹여 보세요!")
        String content
) {
        public static SaveCommentResponse from(Comment comment) {
            return SaveCommentResponse.builder()
                    .id(comment.getId())
                    .content(comment.getContent())
                    .build();
        }
}
