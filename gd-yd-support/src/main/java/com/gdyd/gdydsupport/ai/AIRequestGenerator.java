package com.gdyd.gdydsupport.ai;

import feign.FeignException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Getter
@Component
@RequiredArgsConstructor
public class AIRequestGenerator {
    @Value("${ai.bot.email}")
    private String aiBotEmail;
    @Value("${ai.bot.password}")
    private String aiBotPassword;
    private final AIBotClient aiBotClient;
    private final AIFilteringClient aiFilteringClient;

    public void sendAutoAnswerRequest(AutoAnswerRequest request) {
        try {
            aiBotClient.sendAutoAnswerRequest(request);
        } catch (FeignException e) {
        }
    }

    public ProfanityFilteringResponse sendAbuseFilteringRequest(ProfanityFilteringRequest request) {
        try {
            return aiFilteringClient.sendAbuseFilteringRequest(request);
        } catch (FeignException e) {
            return ProfanityFilteringResponse.from(Boolean.FALSE);
        }
    }
}
