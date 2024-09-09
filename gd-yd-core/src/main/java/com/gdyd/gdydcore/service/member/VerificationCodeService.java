package com.gdyd.gdydcore.service.member;

import com.gdyd.gdydcore.domain.member.VerificationCode;
import com.gdyd.gdydcore.repository.member.VerificationCodeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class VerificationCodeService {
    public final VerificationCodeRepository verificationCodeRepository;

    public VerificationCode getVerificationCodeByEmail(String email) {
        return verificationCodeRepository.findById(email)
                .orElse(null);
    }

    public void save(VerificationCode verificationCode) {
        verificationCodeRepository.save(verificationCode);
    }
}
