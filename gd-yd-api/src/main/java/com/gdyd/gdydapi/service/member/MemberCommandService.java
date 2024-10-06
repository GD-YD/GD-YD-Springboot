package com.gdyd.gdydapi.service.member;

import com.gdyd.gdydapi.request.member.UpdateProfileRequest;
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
@Transactional
public class MemberCommandService {
    private final MemberService memberService;
    private final UniversityStudentService universityStudentService;
    private final HighSchoolStudentService highSchoolStudentService;

    /**
     * 프로필 업데이트
     * -> 닉네임, 학년, 프로필 이미지 URL, 대학교 학과 카테고리(고등학생은 비우면 됩니다.)
     */
    public ProfileResponse updateProfile(UpdateProfileRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);

        if (memberService.existingNickname(request.nickname())) {
            throw new BusinessException(ErrorCode.INVALID_NICKNAME);
        }

        if (member.getType() == MemberType.UNIVERSITY_STUDENT) {
            UniversityStudent universityStudent = universityStudentService.getUniversityStudentByMemberId(memberId);
            universityStudent.updateNickname(request.nickname());
            universityStudent.updateProfileImage(request.profileImageUrl());
            universityStudent.updateGrade(request.grade());
            universityStudent.updateUniversityMajorCategory(request.universityMajorCategory());
            universityStudentService.save(universityStudent);
        } else {
            HighSchoolStudent highSchoolStudent = highSchoolStudentService.getHighSchoolStudentByMemberId(memberId);
            highSchoolStudent.updateNickname(request.nickname());
            highSchoolStudent.updateProfileImage(request.profileImageUrl());
            highSchoolStudent.updateGrade(request.grade());
            highSchoolStudentService.save(highSchoolStudent);
        }
        return ProfileResponse.from(member);
    }
}
