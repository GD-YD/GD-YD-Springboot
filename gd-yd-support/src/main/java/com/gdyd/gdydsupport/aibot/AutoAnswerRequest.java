package com.gdyd.gdydsupport.aibot;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(description = "AI 서버 자동 답변 요청")
public record AutoAnswerRequest(

        @Schema(description = "액세스 토큰", example = "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1...")
        String accessToken,

        @Schema(description = "질문글 ID", example = "1")
        Long questionID,

        @Schema(description = "질문글 내용", example = "12344로 해당 대학에 입학할 수 있을까요?")
        String question
) {
        public static AutoAnswerRequest of(String accessToken, Long questionID, String question) {
                return AutoAnswerRequest.builder()
                        .accessToken(accessToken)
                        .questionID(questionID)
                        .question(question)
                        .build();
        }
}
