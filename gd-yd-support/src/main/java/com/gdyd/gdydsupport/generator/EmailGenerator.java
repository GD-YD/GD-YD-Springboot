package com.gdyd.gdydsupport.generator;

import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Random;

@Component
@RequiredArgsConstructor
public class EmailGenerator {
    private static final Integer EXPIRATION_TIME_IN_MINUTES = 5;
    private final JavaMailSender javaMailSender;

    public String createCode() {
        Random random = new Random();
        StringBuilder code = new StringBuilder();

        for (int i = 0; i < 6; i++) {
            int index = random.nextInt(4);
            code.append(switch (index) {
                case 0 -> (char) (random.nextInt(26) + 97);
                case 1 -> (char) (random.nextInt(26) + 65);
                default -> (char) (random.nextInt(10) + 48);
            });
        }
        return code.toString();
    }

    public LocalDateTime calculateExpireTime() {
        LocalDateTime currentTime = LocalDateTime.now();
        return currentTime.plusMinutes(EXPIRATION_TIME_IN_MINUTES);
    }

    public String sendMail(String mail) {
        String code = createCode();
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(message, false, "UTF-8");
            mimeMessageHelper.setTo(mail);
            mimeMessageHelper.setSubject("고대연대 이메일 인증");
            String body = "";
            body += "<h3>" + "고대연대 이메일 인증을 위한 인증코드입니다." + "</h3>";
            body += "<div style='border: 2px solid black; padding: 10px; display: inline-block;'>" +
                    "<h1>" + code + "</h1>" +
                    "</div>";
            body += "<h3>" + "6자리 코드를 정확히 입력해주세요!" + "</h3>";
            mimeMessageHelper.setText(body, true);
            javaMailSender.send(message);
        } catch (MailException | MessagingException e) {
            throw new BusinessException(ErrorCode.EMAIL_SEND_FAILURE);
        }
        return code;
    }
}
