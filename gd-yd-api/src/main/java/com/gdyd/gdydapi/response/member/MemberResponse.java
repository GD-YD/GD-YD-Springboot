package com.gdyd.gdydapi.response.member;

import com.gdyd.gdydcore.domain.member.Member;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
@Schema(name = "MemberResponse", description = "자유게시판 멤버 응답")
public record MemberResponse(
        @Schema(description = "이메일", example = "test@gmail.com")
        String email,

        @Schema(description = "닉네임", example = "인천교회출신스님")
        String nickname,

        @Schema(description = "이름", example = "이재영")
        String name
) {
        public static MemberResponse from(Member member) {
            return MemberResponse.builder()
                    .email(member.getEmail())
                    .nickname(member.getNickname())
                    .name(member.getName())
                    .build();
        }
}
