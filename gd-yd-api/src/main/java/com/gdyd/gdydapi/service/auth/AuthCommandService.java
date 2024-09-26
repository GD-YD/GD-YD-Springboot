package com.gdyd.gdydapi.service.auth;

import com.gdyd.gdydapi.request.auth.*;
import com.gdyd.gdydapi.response.auth.LoginResponse;
import com.gdyd.gdydapi.response.auth.SendMailResponse;
import com.gdyd.gdydapi.response.auth.SignUpResponse;
import com.gdyd.gdydapi.response.auth.VerifyCodeResponse;
import com.gdyd.gdydapi.runner.HighSchoolNameCache;
import com.gdyd.gdydapi.runner.UniversityNameCache;
import com.gdyd.gdydauth.jwt.JwtProvider;
import com.gdyd.gdydauth.jwt.JwtValidationType;
import com.gdyd.gdydauth.jwt.Token;
import com.gdyd.gdydauth.jwt.UserAuthentication;
import com.gdyd.gdydcore.domain.member.*;
import com.gdyd.gdydcore.service.member.*;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import com.gdyd.gdydsupport.generator.EmailGenerator;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthCommandService {
    private final HighSchoolStudentService highSchoolStudentService;
    private final UniversityStudentService universityStudentService;
    private final MemberService memberService;
    private final VerificationCodeService verificationCodeService;
    private final EmailGenerator emailGenerator;
    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;

    private final UniversityNameCache universityNameCache;
    private final HighSchoolNameCache highSchoolNameCache;

    public SignUpResponse signupHighSchool(HighSchoolSignUpRequest request) {
        if (memberService.existingEmail(request.email()) || memberService.existingNickname(request.nickname())) {
            throw new BusinessException(ErrorCode.INVALID_SIGNUP);
        }
        HighSchoolStudent student = HighSchoolSignUpRequest.toHighSchoolStudent(request);
        if (highSchoolNameCache.isValidHighSchool(student.getHighSchoolName())) {
            throw new BusinessException(ErrorCode.INVALID_HIGH_SCHOOL_NAME, student.getHighSchoolName());
        }
        student.updatePassword(passwordEncoder.encode(request.password()));
        verificationCodeService.delete(request.email());

        highSchoolStudentService.save(student);
        return SignUpResponse.from(student);
    }

    public SignUpResponse signupUniversity(UniversitySignUpRequest request) {
        if (memberService.existingEmail(request.email()) || memberService.existingNickname(request.nickname())) {
            throw new BusinessException(ErrorCode.INVALID_SIGNUP);
        }
        UniversityStudent student = UniversitySignUpRequest.toUniversityStudent(request);
        if (universityNameCache.isValidUniversity(student.getUniversityName())) {
            throw new BusinessException(ErrorCode.INVALID_UNIVERSITY_NAME, student.getUniversityName());
        }
        student.updatePassword(passwordEncoder.encode(request.password()));
        verificationCodeService.delete(request.email());

        universityStudentService.save(student);
        return SignUpResponse.from(student);
    }

    public SendMailResponse sendMail(SendMailRequest request) {
        String code = emailGenerator.sendMail(request.email());
        LocalDateTime expireTime = emailGenerator.calculateExpireTime();
        VerificationCode verificationCode;
        if (verificationCodeService.existsByEmail(request.email())) {
            verificationCode = verificationCodeService.getVerificationCodeByEmail(request.email());
            verificationCode.updateCode(code);
            verificationCode.updateExpireTime(expireTime);
        }
        else {
            verificationCode = new VerificationCode(request.email(), code, expireTime);
            verificationCodeService.save(verificationCode);
        }
        return SendMailResponse.from(code);
    }

    public VerifyCodeResponse verifyCode(VerifyCodeRequest request) {
        VerificationCode verificationCode = verificationCodeService.getVerificationCodeByEmail(request.email());
        Boolean isMatch = verificationCode.isMatch(request.email(), request.code());
        Boolean isExpired = verificationCode.isExpired();
        if (Boolean.TRUE.equals(isExpired)) {
            verificationCodeService.delete(request.email());
        }
        return VerifyCodeResponse.of(isMatch, isExpired);
    }

    /**
     * Login Logic if already have Refresh Token Then delete it and create new Refresh Token
     * @param request LoginRequest
     * @return LoginResponse
     */
    public LoginResponse login(LoginRequest request) {
        Member member = memberService.getMemberByEmailandPassword(request.email(), request.password());
        if (refreshTokenService.existsByMemberId(member.getId())) {
            RefreshToken refreshToken = refreshTokenService.getBymemberId(member.getId());
            refreshTokenService.delete(refreshToken);
        }

        Authentication authentication = new UserAuthentication(member.getId(), null, null);
        Token generatedaccessToken = jwtProvider.generateAccessToken(authentication);
        Token generatedRefreshToken = jwtProvider.generateRefreshToken(authentication);

        RefreshToken refreshToken = RefreshToken.builder()
                .memberId(member.getId())
                .token(generatedRefreshToken.getValue())
                .expiredAt(generatedRefreshToken.getExpiredTime())
                .build();
        refreshTokenService.save(refreshToken);

        return LoginResponse.of(generatedaccessToken, generatedRefreshToken);
    }

    public LoginResponse refreshAccessToken(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        Member member;
        RefreshToken refreshTokenInDB;
        JwtValidationType validationType = jwtProvider.validateRefreshToken(refreshToken);
        Token accessToken = switch (validationType) {
            case VALID_JWT -> {
                Long memberId = jwtProvider.getMemberIdByRefreshToken(refreshToken);
                member = memberService.getMemberById(memberId);
                refreshTokenInDB = refreshTokenService.getBymemberId(memberId);
                if (!refreshToken.equals(refreshTokenInDB.getToken())) {
                    throw new BusinessException(ErrorCode.INVALID_REFRESH_TOKEN);
                }

                Authentication authentication = new UserAuthentication(member.getId(), null, null);
                Token newRefreshToken = jwtProvider.generateRefreshToken(authentication);
                refreshTokenInDB.updateToken(newRefreshToken.getValue());
                refreshTokenInDB.updateExpiredAt(newRefreshToken.getExpiredTime());
                refreshTokenService.save(refreshTokenInDB);
                yield jwtProvider.generateAccessToken(authentication);
            }
            case EXPIRED_JWT -> throw new BusinessException(ErrorCode.EXPIRED_JWT);
            case INVALID_JWT_SIGNATURE -> throw new BusinessException(ErrorCode.INVALID_JWT_SIGNATURE);
            case INVALID_JWT -> throw new BusinessException(ErrorCode.INVALID_JWT);
            case EMPTY_JWT -> throw new BusinessException(ErrorCode.EMPTY_TOKEN);
            case UNSUPPORTED_JWT -> throw new BusinessException(ErrorCode.UNSUPPORTED_JWT);
            default -> throw new IllegalArgumentException("Unknown JwtValidationType: " + validationType);
        };
        return LoginResponse.of(accessToken.getValue(), refreshTokenInDB.getToken());
    }

    public void logout(RefreshTokenRequest request) {
        String refreshToken = request.refreshToken();
        Long memberId = jwtProvider.getMemberIdByRefreshToken(refreshToken);
        RefreshToken refreshTokenInDB = refreshTokenService.getBymemberId(memberId);
        refreshTokenService.delete(refreshTokenInDB);
    }

    public void changePassword(PasswordChangeRequest request) {
        Member member = memberService.getMemberByEmailandPassword(request.email(), request.oldPassword());
        member.updatePassword(passwordEncoder.encode(request.newPassword()));
        memberService.save(member);
    }

    public SendMailResponse sendMailForPassword(SendMailRequest request) {
        if (!memberService.existingEmail(request.email())) {
            throw new BusinessException(ErrorCode.NOT_FOUND_MEMBER);
        }
        return sendMail(request);
    }

    public void resetPassword(ResetPasswordRequest request) {
        if (!verificationCodeService.existsByEmail(request.email())) {
            throw new BusinessException(ErrorCode.NOT_FOUND_VERIFICATION_CODE);
        }
        Member member = memberService.getMemberByEmail(request.email());
        member.updatePassword(passwordEncoder.encode(request.newPassword()));
        verificationCodeService.delete(request.email());
    }
}
