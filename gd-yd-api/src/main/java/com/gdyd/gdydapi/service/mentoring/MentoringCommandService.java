package com.gdyd.gdydapi.service.mentoring;

import com.gdyd.gdydapi.request.mentoring.CreateHighSchoolStudentQuestionRequest;
import com.gdyd.gdydapi.request.mentoring.CreateUniversityStudentAnswerRequest;
import com.gdyd.gdydapi.response.common.LikeListResponse;
import com.gdyd.gdydapi.response.mentoring.CreateHighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.response.mentoring.CreateUniversityStudentAnswerResponse;
import com.gdyd.gdydapi.service.member.MemberQueryService;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.domain.member.Member;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import com.gdyd.gdydcore.service.member.LikeListService;
import com.gdyd.gdydcore.service.member.MemberService;
import com.gdyd.gdydcore.service.mentoring.HighSchoolStudentQuestionService;
import com.gdyd.gdydcore.service.mentoring.UniversityStudentAnswerService;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class MentoringCommandService {
    private final HighSchoolStudentQuestionService highSchoolStudentQuestionService;
    private final UniversityStudentAnswerService universityStudentAnswerService;
    private final MemberQueryService memberQueryService;
    private final MemberService memberService;
    private final LikeListService likeListService;

    /**
     * 고등학생 질문 생성
     * @param request 고등학생 질문 생성 요청
     */
    public CreateHighSchoolStudentQuestionResponse createHighSchoolStudentQuestion(CreateHighSchoolStudentQuestionRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        HighSchoolStudent highSchoolStudent = memberQueryService.getHighSchoolStudentByMemberId(memberId);
        HighSchoolStudentQuestion question = CreateHighSchoolStudentQuestionRequest.toHighSchoolStudentQuestion(request, highSchoolStudent);
        highSchoolStudentQuestionService.save(question);
        return CreateHighSchoolStudentQuestionResponse.from(question);
    }

    /**
     * 대학생 답변 생성
     * @param highSchoolStudentQuestionId 고등학생 질문글 ID
     * @param request 대학생 답변 생성 요청
     */
    public CreateUniversityStudentAnswerResponse createUniversityStudentAnswer(Long highSchoolStudentQuestionId, CreateUniversityStudentAnswerRequest request) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        UniversityStudent universityStudent = memberQueryService.getUniversityStudentByMemberId(memberId);
        HighSchoolStudentQuestion question = highSchoolStudentQuestionService.getHighSchoolStudentQuestionById(highSchoolStudentQuestionId);
        UniversityStudentAnswer answer = CreateUniversityStudentAnswerRequest.toUniversityStudentAnswer(request, universityStudent, question);

        universityStudentAnswerService.save(answer);
        question.increaseAnswerCount();
        return CreateUniversityStudentAnswerResponse.from(answer);
    }

    /**
     * 고등학생 질문 좋아요 증가
     */
    public LikeListResponse likeHighSchoolStudentQuestion(Long highSchoolStudentQuestionId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        HighSchoolStudentQuestion question = highSchoolStudentQuestionService.getHighSchoolStudentQuestionById(highSchoolStudentQuestionId);

        if (likeListService.existsByMemberIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId)) {
            throw new BusinessException(ErrorCode.AREADY_LIKED);
        }

        question.increaseLikeCount();
        LikeList likeList = LikeList.highSchoolStudentQuestionLikeBuilder()
                .member(member)
                .highSchoolStudentQuestion(question)
                .highSchoolStudentQuestionLikeBuild();
        likeListService.save(likeList);
        return LikeListResponse.from(likeList);
    }

    /**
     * 고등학생 질문 좋아요 감소
     */
    public LikeListResponse dislikeHighSchoolStudentQuestion(Long highSchoolStudentQuestionId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        HighSchoolStudentQuestion question = highSchoolStudentQuestionService.getHighSchoolStudentQuestionById(highSchoolStudentQuestionId);

        if (!likeListService.existsByMemberIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId)) {
            throw new BusinessException(ErrorCode.AREADY_UNLIKED);
        }

        question.decreaseLikeCount();
        LikeList likeList = member.getLikeLists().stream()
                .filter(like -> like.getHighSchoolStudentQuestion().getId().equals(highSchoolStudentQuestionId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.AREADY_UNLIKED));
        likeListService.delete(likeList);
        return LikeListResponse.from(likeList);
    }

    /**
     * 대학생 답변 좋아요 증가
     */
    public LikeListResponse likeUniversityStudentAnswer(Long universityStudentAnswerId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        UniversityStudentAnswer answer = universityStudentAnswerService.getUniversityStudentAnswerById(universityStudentAnswerId);

        if (likeListService.existsByMemberIdAndUniversityStudentAnswerId(memberId, universityStudentAnswerId)) {
            throw new BusinessException(ErrorCode.AREADY_LIKED);
        }

        answer.increaseLikeCount();
        LikeList likeList = LikeList.universityStudentAnswerLikeBuilder()
                .member(member)
                .universityStudentAnswer(answer)
                .universityStudentAnswerLikeBuild();
        likeListService.save(likeList);
        return LikeListResponse.from(likeList);
    }

    /**
     * 대학생 답변 좋아요 감소
     */
    public LikeListResponse dislikeUniversityStudentAnswer(Long universityStudentAnswerId) {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        Member member = memberService.getMemberById(memberId);
        UniversityStudentAnswer answer = universityStudentAnswerService.getUniversityStudentAnswerById(universityStudentAnswerId);

        if (!likeListService.existsByMemberIdAndUniversityStudentAnswerId(memberId, universityStudentAnswerId)) {
            throw new BusinessException(ErrorCode.AREADY_UNLIKED);
        }

        answer.decreaseLikeCount();
        LikeList likeList = member.getLikeLists().stream()
                .filter(like -> like.getUniversityStudentAnswer().getId().equals(universityStudentAnswerId))
                .findFirst()
                .orElseThrow(() -> new BusinessException(ErrorCode.AREADY_UNLIKED));
        likeListService.delete(likeList);
        return LikeListResponse.from(likeList);
    }
}
