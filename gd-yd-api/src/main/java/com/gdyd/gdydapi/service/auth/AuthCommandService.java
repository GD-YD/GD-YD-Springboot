package com.gdyd.gdydapi.service.auth;

import com.gdyd.gdydapi.request.auth.HighSchoolSignUpRequest;
import com.gdyd.gdydapi.request.auth.UniversitySignUpRequest;
import com.gdyd.gdydapi.response.auth.SignUpResponse;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.service.member.HighSchoolStudentService;
import com.gdyd.gdydcore.service.member.UniversityStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthCommandService {
    private final HighSchoolStudentService highSchoolStudentService;
    private final UniversityStudentService universityStudentService;

    private final PasswordEncoder passwordEncoder;

    public SignUpResponse signupHighSchool(HighSchoolSignUpRequest request) {
        if (highSchoolStudentService.existsHighSchoolStudentByEmail(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        HighSchoolStudent student = HighSchoolSignUpRequest.toHighSchoolStudent(request);
        student.updatePassword(passwordEncoder.encode(request.password()));

        highSchoolStudentService.save(student);
        return SignUpResponse.from(student);
    }

    public SignUpResponse signupUniversity(UniversitySignUpRequest request) {
        if (universityStudentService.existsUniversityStudentByEmail(request.email())) {
            throw new IllegalArgumentException("이미 가입된 이메일입니다.");
        }
        UniversityStudent student = UniversitySignUpRequest.toUniversityStudent(request);
        student.updatePassword(passwordEncoder.encode(request.password()));

        universityStudentService.save(student);
        return SignUpResponse.from(student);
    }
}
