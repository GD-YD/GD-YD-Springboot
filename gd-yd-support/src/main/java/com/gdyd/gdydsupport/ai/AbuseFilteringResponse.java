package com.gdyd.gdydsupport.ai;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

import java.util.List;

@Builder
@Schema(description = "AI 서버 욕설 필터링 응답")
public record AbuseFilteringResponse(

        @Schema(description = "욕설 포함 여부", example = "True")
        Boolean isAbuseDetected,

        @Schema(description = "욕설 필터링 사유")
        List<String> reasons
) {
}
