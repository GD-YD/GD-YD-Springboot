package com.gdyd.gdydsupport.ai;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${ai.bot.name}", url = "${ai.bot.endpoint}")
public interface AIBotClient {
    @PostMapping
    void sendAutoAnswerRequest(@RequestBody AutoAnswerRequest request);
}
