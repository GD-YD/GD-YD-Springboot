package com.gdyd.gdydapi.controller.auth;

import com.gdyd.gdydapi.request.auth.*;
import com.gdyd.gdydapi.response.auth.LoginResponse;
import com.gdyd.gdydapi.response.auth.SendMailResponse;
import com.gdyd.gdydapi.response.auth.SignUpResponse;
import com.gdyd.gdydapi.response.auth.VerifyCodeResponse;
import com.gdyd.gdydapi.service.auth.AuthCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Auth", description = "Auth 관련 API")
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthCommandService authCommandService;

    @Operation(summary = "고등학생 회원가입 API", description = "고등학생 회원가입을 할 수 있는 API")
    @PostMapping("/signup/highschool")
    public ResponseEntity<SignUpResponse> signupHighSchool(
            @RequestBody @Valid HighSchoolSignUpRequest request
    ) {
        SignUpResponse response = authCommandService.signupHighSchool(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "대학생 회원가입 API", description = "대학생 회원가입을 할 수 있는 API")
    @PostMapping("/signup/university")
    public ResponseEntity<SignUpResponse> signupUniversity(
            @RequestBody @Valid UniversitySignUpRequest request
    ) {
        SignUpResponse response = authCommandService.signupUniversity(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "이메일 인증 API", description = "이메일 인증을 요청하는 API")
    @PostMapping("/send-verification-email")
    public ResponseEntity<SendMailResponse> sendVerificationMail(
            @RequestBody @Valid SendMailRequest request
    ) {
        SendMailResponse response = authCommandService.sendMail(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "코드 인증 API", description = "이메일로 받은 코드에 대한 인증을 요청하는 API")
    @PostMapping("/verify-code")
    public ResponseEntity<VerifyCodeResponse> verifyCode(
            @RequestBody @Valid VerifyCodeRequest request
    ) {
        VerifyCodeResponse response = authCommandService.verifyCode(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그인 API", description = "이메일을 기반으로 로그인해 Access, Refresh 토큰을 발급 할 수 있는 API")
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @RequestBody @Valid LoginRequest request
    ) {
        LoginResponse response = authCommandService.login(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Refresh 토큰을 통한 Access, Refresh 토큰 재발급 API", description = "Refresh 토큰을 통해 Access, Refresh 토큰을 재발급 할 수 있는 API")
    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshAccessToken(
            @RequestBody @Valid RefreshTokenRequest request
    ) {
        LoginResponse response = authCommandService.refreshAccessToken(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "로그아웃 API", description = "Refresh 토큰을 통해 로그아웃을 하고, DB의 Refresh 토큰을 제거하는 API (반드시 새로 로그인하기 전에는 사용해야 함)")
    @DeleteMapping("/logout")
    public ResponseEntity<HttpStatus> logout(
            @RequestBody @Valid RefreshTokenRequest request
    ) {
        authCommandService.logout(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "비밀번호 변경 API", description = "비밀번호를 변경할 수 있는 API")
    @PatchMapping("/password")
    public ResponseEntity<HttpStatus> changePassword(
            @RequestBody @Valid PasswordChangeRequest request
    ) {
        authCommandService.changePassword(request);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "비밀번호 찾기를 위한 인증 이메일 전송 API", description = "비밀번호 찾기(초기화)시 본인 인증을 위한 이메일 전송 요청")
    @PostMapping("/forgot-password/send-verification-email")
    public ResponseEntity<SendMailResponse> sendMailForPassword(
            @RequestBody @Valid SendMailRequest request
    ) {
        SendMailResponse response = authCommandService.sendMailForPassword(request);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "비밀번호 리셋 API", description = "이메일 인증 후 비밀번호 초기화 요청 API")
    @PostMapping("/forgot-password")
    public ResponseEntity<HttpStatus> resetPassword(
            @RequestBody @Valid ResetPasswordRequest request
    ) {
        authCommandService.resetPassword(request);
        return ResponseEntity.noContent().build();
    }
}
