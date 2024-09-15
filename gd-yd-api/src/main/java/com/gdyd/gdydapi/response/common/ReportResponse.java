package com.gdyd.gdydapi.response.common;

import com.gdyd.gdydcore.domain.report.Report;
import com.gdyd.gdydcore.domain.report.ReportType;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "신고하기 응답")
public record ReportResponse(
        @Schema(description = "신고자 ID", example = "1")
        Long reporterId,

        @Schema(description = "신고 타입", example = "POST")
        ReportType reportType
) {
    public static ReportResponse from(Report report) {
         return ReportResponse.builder()
                 .reporterId(report.getReporter().getId())
                 .reportType(report.getType())
                 .build();
    }
}
