package com.gdyd.gdydapi.request.report;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "신고 알림용 디스코드 전송 메시지")
public record ReportDiscordRequest(

        @Schema(description = "디스코드 메시지 내용")
        String content
) {
    public static ReportDiscordRequest createDiscordMessage(String message) {
        return new ReportDiscordRequest(message);
    }
}
