package com.gdyd.gdydsupport.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "AI 서버 욕설 필터링 요청")
public record ProfanityFilteringRequest(

        @Schema(description = "필터링 대상 글 제목", example = "상담할 게 있어요")
        String title,

        @Schema(description = "필터링 대상 글 내용", example = "여자친구가 절 무시해요")
        String content
) {
        public static ProfanityFilteringRequest of(String title, String content) {
                return ProfanityFilteringRequest.builder()
                        .title(title)
                        .content(content)
                        .build();
        }

        public static ProfanityFilteringRequest from(String content) {
                return ProfanityFilteringRequest.builder()
                        .title("")
                        .content(content)
                        .build();
        }
}
