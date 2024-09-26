package com.gdyd.gdydapi.service.member;

import com.gdyd.gdydapi.request.member.UpdateProfileRequest;
import com.gdyd.gdydapi.response.member.ProfileResponse;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.service.member.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MemberCommandService {
    private final MemberService memberService;

    public ProfileResponse updateProfile(UpdateProfileRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        member.updateEmail(request.email());
        member.updateNickname(request.nickname());
        return ProfileResponse.from(member);
    }
}
