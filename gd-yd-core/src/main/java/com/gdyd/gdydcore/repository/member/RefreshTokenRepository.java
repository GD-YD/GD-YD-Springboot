package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
}
