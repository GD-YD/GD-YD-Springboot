package com.gdyd.gdydcore.service.member;

import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.repository.member.MemberRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }

    public Member getMemberByEmailandPassword(String email, String password) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
        if (!passwordEncoder.matches(password, member.getPassword())) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_PASSWORD);
        }
        return member;
    }

    public Member getMemberByEmail(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }

    public boolean existingEmail(String email) {
        return memberRepository.existsByEmail(email);
    }

    public boolean existingNickname(String nickname) {
        return memberRepository.existsByNickname(nickname);
    }

    @Transactional
    public void save(Member member) {
        memberRepository.save(member);
    }
}
