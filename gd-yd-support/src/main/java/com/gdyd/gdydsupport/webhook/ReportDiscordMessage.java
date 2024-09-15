package com.gdyd.gdydsupport.webhook;

import lombok.Builder;
import io.swagger.v3.oas.annotations.media.Schema;

@Builder
@Schema(description = "신고 알림용 디스코드 메시지")
public record ReportDiscordMessage(
        String content
) {
    public static ReportDiscordMessage from(String content) {
        return ReportDiscordMessage.builder()
                .content(content)
                .build();
    }
}
