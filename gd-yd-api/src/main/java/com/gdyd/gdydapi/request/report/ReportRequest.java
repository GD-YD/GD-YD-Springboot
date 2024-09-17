package com.gdyd.gdydapi.request.report;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "신고 요청")
public record ReportRequest(
        @Schema(description = "신고 내용", example = "선정적인 내용")
        String content
) {
}
