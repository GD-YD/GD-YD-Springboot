package com.gdyd.gdydsupport.webhook;

public record ReportDiscordMessage(
        String content
) {
    public static ReportDiscordMessage createDiscordMessage(String message) {
        return new ReportDiscordMessage(message);
    }
}
