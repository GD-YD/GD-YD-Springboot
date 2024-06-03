package com.gdyd.gdydapi.service.member;

import com.gdyd.gdydapi.response.member.ProfileResponse;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.member.MemberType;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.service.member.HighSchoolStudentService;
import com.gdyd.gdydcore.service.member.MemberService;
import com.gdyd.gdydcore.service.member.UniversityStudentService;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberService memberService;
    private final UniversityStudentService universityStudentService;
    private final HighSchoolStudentService highSchoolStudentService;

    public boolean existingEmail(String email) {
        return memberService.existingEmail(email);
    }

    public boolean existingNickname(String nickname) {
        return memberService.existingNickname(nickname);
    }

    public ProfileResponse getProfile() {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        return ProfileResponse.from(member);
    }

    public HighSchoolStudent getHighSchoolStudentByMemberId(Long memberId) {
        MemberType memberType = memberService.getMemberById(memberId).getType();
        if (memberType != MemberType.HIGH_SCHOOL_STUDENT) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_REQUEST);
        }
        return highSchoolStudentService.getHighSchoolStudentByMemberId(memberId);
    }

    public UniversityStudent getUniversityStudentByMemberId(Long memberId) {
        MemberType memberType = memberService.getMemberById(memberId).getType();
        if (memberType != MemberType.UNIVERSITY_STUDENT) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_REQUEST);
        }
        return universityStudentService.getUniversityStudentByMemberId(memberId);
    }
}
