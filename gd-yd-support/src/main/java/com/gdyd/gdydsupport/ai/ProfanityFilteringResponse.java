package com.gdyd.gdydsupport.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@Schema(description = "AI 서버 욕설 필터링 응답")
public record ProfanityFilteringResponse(

        @Schema(description = "욕설 포함 여부", example = "True")
        Boolean isProfanityDetected
) {
}
