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

    public String postReportMessage(String reporterEmail, Long postId, String title, String content, String reason) {
        String formattedMessage = String.format(
                "## 신고 알림\n" +
                        "> **신고자**: %s\n" +
                        "> \n" +
                        "> **신고 대상**: 게시글    `ID: %d`\n" +
                        ">    제목: %s\n" +
                        ">    본문: %s\n" +
                        "> \n" +
                        "> **신고 사유**: %s",
                reporterEmail, postId, title, content, reason
        );

        return formattedMessage;
    }

    public String commentReportMessage(String reporterEmail, Long commentId, String content, String reason) {
        String formattedMessage = String.format(
                "## 신고 알림\n" +
                        "> **신고자**: %s\n" +
                        "> \n" +
                        "> **신고 대상**: 댓글    `ID: %d`\n" +
                        ">    내용: %s\n" +
                        "> \n" +
                        "> **신고 사유**: %s",
                reporterEmail, commentId, content, reason
        );

        return formattedMessage;
    }

    public String questionReportMessage(String reporterEmail, Long questionId, String title, String question, String reason) {
        String formattedMessage = String.format(
                "## 신고 알림\n" +
                        "> **신고자**: %s\n" +
                        "> \n" +
                        "> **신고 대상**: 고등학생 질문글    `ID: %d`\n" +
                        ">    제목: %s\n" +
                        ">    질문: %s\n" +
                        "> \n" +
                        "> **신고 사유**: %s",
                reporterEmail, questionId, title, question, reason

        );

        return formattedMessage;
    }

    public String answerReportMessage(String reporterEmail, Long answerId, String answer, String reason) {
        String formattedMessage = String.format(
                "## 신고 알림\n" +
                        "> **신고자**: %s\n" +
                        "> \n" +
                        "> **신고 대상**: 대학생 답변글    `ID: %d`\n" +
                        ">    내용: %s\n" +
                        "> \n" +
                        "> **신고 사유**: %s",
                reporterEmail, answerId, answer, reason
        );

        return formattedMessage;
    }
}
