package com.gdyd.gdydsupport.aibot;

import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AIBotRequestGenerator {
    private final AIBotClient aiBotClient;

    public void sendAutoAnswerRequest(AutoAnswerRequest request) {
        try {
            aiBotClient.sendAutoAnswerRequest(request);
        } catch (FeignException e) {
            throw new BusinessException(ErrorCode.REQUEST_FAILURE);
        }
    }
}
