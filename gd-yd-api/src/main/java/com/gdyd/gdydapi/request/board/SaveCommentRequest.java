package com.gdyd.gdydapi.request.board;

import com.gdyd.gdydcore.domain.board.Comment;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Comment 생성 요청")
public record SaveCommentRequest(
        @Schema(description = "Comment 내용", example = "그럴땐 맛있는 걸 사 먹여 보세요!")
        String content,

        @Schema(description = "Member ID", example = "1")
        Long memberId,

        @Schema(description = "Post ID", example = "1")
        Long postId
) {
        public static Comment toComment(SaveCommentRequest request) {
            return Comment.builder()
                    .content(request.content())
                    .build();
        }
}
