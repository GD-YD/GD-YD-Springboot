package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.member.RefreshToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByMemberId(Long memberId);

    boolean existsByMemberId(Long memberId);
}
