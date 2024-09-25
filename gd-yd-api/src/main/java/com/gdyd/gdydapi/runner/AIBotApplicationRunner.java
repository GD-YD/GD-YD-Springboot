package com.gdyd.gdydapi.runner;

import com.gdyd.gdydapi.request.auth.UniversitySignUpRequest;
import com.gdyd.gdydcore.domain.member.Grade;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.service.member.MemberService;
import com.gdyd.gdydcore.service.member.UniversityStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AIBotApplicationRunner implements ApplicationRunner {
    @Value("${ai.bot.email}")
    private String aiBotEmail;
    @Value("${ai.bot.password}")
    private String aiBotPassword;
    private final MemberService memberService;
    private final UniversityStudentService universityStudentService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        if (memberService.existingEmail(aiBotEmail)) {
            return ;
        }
        UniversitySignUpRequest request = new UniversitySignUpRequest(
                "AI_BOT",
                "GDYD_BOT",
                aiBotEmail,
                aiBotPassword,
                "단국대학교",
                Grade.FIRST,
                2024L,
                "COMPUTER_SCIENCE",
                "00000000"
        );
        UniversityStudent aiBot = UniversitySignUpRequest.toUniversityStudent(request);
        aiBot.updatePassword(passwordEncoder.encode(request.password()));
        universityStudentService.save(aiBot);
    }
}
