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

    public void sendMessage(String message) {
        ReportDiscordMessage discordMessage = ReportDiscordMessage.createDiscordMessage(message);
        sendMessageToDiscord(discordMessage);
    }

    private void sendMessageToDiscord(ReportDiscordMessage discordMessage) {
        try {
            discordClient.sendMessage(discordMessage);
        } catch (FeignException e) {
            throw new BusinessException(ErrorCode.EMAIL_SEND_FAILURE);
        }
    }
}
