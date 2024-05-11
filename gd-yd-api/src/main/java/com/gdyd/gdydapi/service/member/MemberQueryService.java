package com.gdyd.gdydapi.service.member;

import com.gdyd.gdydcore.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {

    private final MemberService memberService;

    public boolean existingEmail(String email) {
        return memberService.existingEmail(email);
    }
}
