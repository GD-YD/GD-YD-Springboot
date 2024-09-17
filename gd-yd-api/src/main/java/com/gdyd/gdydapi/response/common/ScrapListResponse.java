package com.gdyd.gdydapi.response.common;

import com.gdyd.gdydcore.domain.member.ScrapList;
import com.gdyd.gdydcore.domain.member.ScrapType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "스크랩 리스트 응답")
public record ScrapListResponse(
        @Schema(description = "멤버 ID", example = "1")
        Long memberId,

        @Schema(description = "스크랩 타입", example = "HIGH_SCHOOL_STUDENT_QUESTION")
        ScrapType scrapType
) {
    public static ScrapListResponse from(ScrapList scrapList) {
        return ScrapListResponse.builder()
                .memberId(scrapList.getMember().getId())
                .scrapType(scrapList.getType())
                .build();
    }
}
