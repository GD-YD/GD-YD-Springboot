package com.gdyd.gdydapi.service.mentoring;

import com.gdyd.gdydapi.request.mentoring.CreateHighSchoolStudentQuestionRequest;
import com.gdyd.gdydapi.request.mentoring.CreateUniversityStudentAnswerRequest;
import com.gdyd.gdydapi.response.mentoring.CreateHighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.response.mentoring.CreateUniversityStudentAnswerResponse;
import com.gdyd.gdydapi.service.member.MemberQueryService;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import com.gdyd.gdydcore.service.mentoring.HighSchoolStudentQuestionService;
import com.gdyd.gdydcore.service.mentoring.UniversityStudentAnswerService;
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
}
