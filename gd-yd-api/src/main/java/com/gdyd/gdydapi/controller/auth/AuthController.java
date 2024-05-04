package com.gdyd.gdydapi.controller.auth;

import com.gdyd.gdydapi.request.auth.HighSchoolSignUpRequest;
import com.gdyd.gdydapi.request.auth.LoginRequest;
import com.gdyd.gdydapi.request.auth.UniversitySignUpRequest;
import com.gdyd.gdydapi.response.auth.LoginResponse;
import com.gdyd.gdydapi.response.auth.SignUpResponse;
import com.gdyd.gdydapi.service.auth.AuthCommandService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public ResponseEntity<SignUpResponse> signup(
            @RequestBody @Valid UniversitySignUpRequest request
    ) {
        SignUpResponse response = authCommandService.signupUniversity(request);
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
}
