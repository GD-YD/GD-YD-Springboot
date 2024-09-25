package com.gdyd.gdydsupport.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "AI 서버 욕설 필터링 요청")
public record AbuseFilteringRequest(

        @Schema(description = "질문글 내용", example = "여자친구가 절 무시해요")
        String content
) {
        public static AbuseFilteringRequest from(String content) {
                return AbuseFilteringRequest.builder()
                        .content(content)
                        .build();
        }
}
