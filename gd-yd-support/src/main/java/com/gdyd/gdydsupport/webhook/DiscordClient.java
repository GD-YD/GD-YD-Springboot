package com.gdyd.gdydsupport.webhook;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "${discord-report-bot.name}", url = "${discord-report-bot.webhook-url}")
public interface DiscordClient {

    @PostMapping
    void sendMessage(@RequestBody ReportDiscordMessage discordMessage);
}
