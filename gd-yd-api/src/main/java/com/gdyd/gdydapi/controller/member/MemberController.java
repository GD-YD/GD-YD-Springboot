package com.gdyd.gdydapi.controller.member;

import com.gdyd.gdydapi.service.member.MemberQueryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Member", description = "Member 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/member")
public class MemberController {
    private final MemberQueryService memberQueryService;

    @Operation(summary = "회원 이메일 중복 조회 API", description = "이메일 중복을 조회할 수 있는 API (true -> 중복된 이메일 존재)")
    @GetMapping("/existing-email")
    public ResponseEntity<Boolean> existingEmail(
            @RequestParam(value = "email", required = true) String email
    ) {
        boolean response = memberQueryService.existingEmail(email);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "회원 닉네임 중복 조회 API", description = "닉네임 중복을 조회할 수 있는 API (true -> 중복된 닉네임 존재)")
    @GetMapping("/existing-nickname")
    public ResponseEntity<Boolean> existingNickname(
            @RequestParam(value = "nickname", required = true) String nickname
    ) {
        boolean response = memberQueryService.existingNickname(nickname);
        return ResponseEntity.ok(response);
    }
}
