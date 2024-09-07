package com.gdyd.gdydapi.response.common;

import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.domain.member.LikeType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "좋아요 리스트 응답")
public record LikeListResponse(
        @Schema(description = "멤버 ID", example = "1")
        Long memberId,

        @Schema(description = "좋아요 타입", example = "HIGH_SCHOOL_STUDENT_QUESTION")
        LikeType likeType
) {
    public static LikeListResponse from(LikeList likeList) {
        return LikeListResponse.builder()
                .memberId(likeList.getMember().getId())
                .likeType(likeList.getType())
                .build();
    }
}
