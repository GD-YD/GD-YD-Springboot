package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.member.VerificationCode;
import org.springframework.data.jpa.repository.JpaRepository;

public interface VerificationCodeRepository extends JpaRepository<VerificationCode, String> {
}
