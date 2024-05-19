package com.gdyd.gdydapi.service.auth;

import com.gdyd.gdydapi.request.auth.*;
import com.gdyd.gdydapi.response.auth.LoginResponse;
import com.gdyd.gdydapi.response.auth.SignUpResponse;
import com.gdyd.gdydauth.jwt.JwtProvider;
import com.gdyd.gdydauth.jwt.JwtValidationType;
import com.gdyd.gdydauth.jwt.Token;
import com.gdyd.gdydauth.jwt.UserAuthentication;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.member.RefreshToken;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.service.member.HighSchoolStudentService;
import com.gdyd.gdydcore.service.member.MemberService;
import com.gdyd.gdydcore.service.member.RefreshTokenService;
import com.gdyd.gdydcore.service.member.UniversityStudentService;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthCommandService {
    private final HighSchoolStudentService highSchoolStudentService;
    private final UniversityStudentService universityStudentService;
    private final MemberService memberService;

    private final PasswordEncoder passwordEncoder;
    private final RefreshTokenService refreshTokenService;
    private final JwtProvider jwtProvider;

    public SignUpResponse signupHighSchool(HighSchoolSignUpRequest request) {
        if (highSchoolStudentService.existsHighSchoolStudentByEmail(request.email())) {
            throw new BusinessException(ErrorCode.INVALID_SIGNUP);
        }
        HighSchoolStudent student = HighSchoolSignUpRequest.toHighSchoolStudent(request);
        student.updatePassword(passwordEncoder.encode(request.password()));

        highSchoolStudentService.save(student);
        return SignUpResponse.from(student);
    }

    public SignUpResponse signupUniversity(UniversitySignUpRequest request) {
        if (universityStudentService.existsUniversityStudentByEmail(request.email())) {
            throw new BusinessException(ErrorCode.INVALID_SIGNUP);
        }
        UniversityStudent student = UniversitySignUpRequest.toUniversityStudent(request);
        student.updatePassword(passwordEncoder.encode(request.password()));

        universityStudentService.save(student);
        return SignUpResponse.from(student);
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
}
