package com.gdyd.gdydapi.service.member;

import com.gdyd.gdydapi.response.board.GetPostSummaryResponse;
import com.gdyd.gdydapi.response.common.PageResponse;
import com.gdyd.gdydapi.response.member.ProfileResponse;
import com.gdyd.gdydapi.response.mentoring.HighSchoolStudentQuestionResponse;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.board.Post;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.member.MemberType;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.service.member.HighSchoolStudentService;
import com.gdyd.gdydcore.service.member.MemberService;
import com.gdyd.gdydcore.service.member.ScrapListService;
import com.gdyd.gdydcore.service.member.UniversityStudentService;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberQueryService {
    private final MemberService memberService;
    private final ScrapListService scrapListService;
    private final UniversityStudentService universityStudentService;
    private final HighSchoolStudentService highSchoolStudentService;

    public PageResponse<GetPostSummaryResponse> getScrapPosts(Pageable pageable) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Page<Post> posts = scrapListService.getScrappedPosts(memberId, pageable);
        return PageResponse.of(posts.getContent().stream().map(GetPostSummaryResponse::from).toList());
    }

    public PageResponse<HighSchoolStudentQuestionResponse> getScrapHighSchoolStudentQuestions(Pageable pageable) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Page<HighSchoolStudentQuestion> highSchoolStudentQuestions = scrapListService.getScrappedHighSchoolStudentQuestions(memberId, pageable);
        return PageResponse.of(highSchoolStudentQuestions.getContent().stream().map(HighSchoolStudentQuestionResponse::from).toList());
    }

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

    public UniversityStudent getUniversityStudentByMemberEmail(String email) {
        MemberType memberType = memberService.getMemberByEmail(email).getType();
        if (memberType != MemberType.UNIVERSITY_STUDENT) {
            throw new BusinessException(ErrorCode.INVALID_MEMBER_REQUEST);
        }
        return universityStudentService.getUniversityStudentByEmail(email);
    }
}
