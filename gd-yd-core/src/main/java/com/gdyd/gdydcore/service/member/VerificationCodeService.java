package com.gdyd.gdydcore.service.member;

import com.gdyd.gdydcore.domain.member.VerificationCode;
import com.gdyd.gdydcore.repository.member.VerificationCodeRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
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
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_VERIFICATION_CODE));
    }

    public boolean existsByEmail(String email) {
        return verificationCodeRepository.existsByEmail(email);
    }

    @Transactional
    public void save(VerificationCode verificationCode) {
        verificationCodeRepository.save(verificationCode);
    }

    @Transactional
    public void delete(String email) {
        verificationCodeRepository.deleteById(email);
    }
}
