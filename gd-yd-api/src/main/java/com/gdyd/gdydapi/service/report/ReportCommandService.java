package com.gdyd.gdydapi.service.report;

import com.gdyd.gdydsupport.webhook.DiscordMessageGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class ReportCommandService {
    private final DiscordMessageGenerator discordMessageGenerator;

    public void discordTest(String message) {
        discordMessageGenerator.sendMessage(message);
    }
}
