package com.gdyd.gdydsupport.webhook;

import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DiscordMessageGenerator {
    private final DiscordClient discordClient;

    public void sendReportMessage(String message) {
        ReportDiscordMessage discordMessage = ReportDiscordMessage.from(message);
        try {
            discordClient.sendMessage(discordMessage);
        } catch (FeignException e) {
            throw new BusinessException(ErrorCode.EMAIL_SEND_FAILURE);
        }
    }

    public String postReportMessage(String reporterEmail, Long targetId, String title, String content, String reason) {
        String formattedMessage = String.format(
                "## 신고 알림\n" +
                        "> **신고자**: %s\n" +
                        "> \n" +
                        "> **신고 대상**: 게시글    `ID: %d`\n" +
                        ">    제목: %s\n" +
                        ">    본문: %s\n" +
                        "> \n" +
                        "> **신고 사유**: %s",
                reporterEmail, targetId, title, content, reason
        );

        return formattedMessage;
    }
}
