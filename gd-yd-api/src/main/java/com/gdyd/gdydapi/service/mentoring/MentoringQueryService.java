package com.gdyd.gdydapi.service.mentoring;

import com.gdyd.gdydapi.response.common.PageResponse;
import com.gdyd.gdydapi.response.mentoring.DetailHighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.response.mentoring.HighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.response.mentoring.RecommendationHighSchoolStudentQuestionResponse;
import com.gdyd.gdydauth.utils.PrincipalUtil;
import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import com.gdyd.gdydcore.service.member.UniversityStudentService;
import com.gdyd.gdydcore.service.mentoring.HighSchoolStudentQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoringQueryService {
    private final HighSchoolStudentQuestionService highSchoolStudentQuestionService;
    private final UniversityStudentService universityStudentService;

    /**
     * 고등학생 질문 목록 조회 (페이지네이션)
     * @param pageable 페이지 네이션 정보
     */
    public PageResponse<HighSchoolStudentQuestionResponse> getHighSchoolStudentQuestions(Pageable pageable) {
        Page<HighSchoolStudentQuestion> pages = highSchoolStudentQuestionService.findHighSchoolStudentQuestionByPagination(pageable);
        return PageResponse.of(pages.getContent().stream().map(HighSchoolStudentQuestionResponse::from).toList());
    }

    public PageResponse<HighSchoolStudentQuestionResponse> getBestHighSchoolStudentQuestions(Long like, LocalDateTime weeksAgo, Pageable pageable) {
        List<HighSchoolStudentQuestion> questions = highSchoolStudentQuestionService.findByLikeCountGreaterThanEqualAndCreatedAtAfter(like, weeksAgo, pageable);
        return PageResponse.of(questions.stream().map(HighSchoolStudentQuestionResponse::from).toList());
    }

    /**
     * 고등학생 질문 상세 조회
     * @param highSchoolStudentQuestionId 고등학생 질문 ID
     */
    public DetailHighSchoolStudentQuestionResponse getHighSchoolStudentQuestionDetail(Long highSchoolStudentQuestionId) {
        HighSchoolStudentQuestion highSchoolStudentQuestion = highSchoolStudentQuestionService.getHighSchoolStudentQuestionById(highSchoolStudentQuestionId);
        List<UniversityStudentAnswer> universityStudentAnswers = highSchoolStudentQuestion.getUniversityStudentAnswers();
        return DetailHighSchoolStudentQuestionResponse.of(highSchoolStudentQuestion, universityStudentAnswers);
    }

    /**
     * 대학생 정보와 고등학생 질문글 태그를 바탕으로 대학생에게 추천할 고등학생 질문글 목록 조회
     * Page를 사용해서 최근 7일간 작성된 질문글과 대학생 정보를 바탕으로 점수를 계산하여 상위 5개의 질문글을 추천
     */
    public PageResponse<RecommendationHighSchoolStudentQuestionResponse> getTopQuestionByTagScore() {
        Long memberId = PrincipalUtil.getMemberIdByPrincipal();
        UniversityStudent universityStudent = universityStudentService.getUniversityStudentByMemberId(memberId);

        LocalDateTime cutoffDate = LocalDateTime.now().minusDays(7);
        Pageable pageable = PageRequest.of(0, 5);
        List<HighSchoolStudentQuestion> questions = highSchoolStudentQuestionService.findTopQuestionsByScore(
                universityStudent.getUniversityName(),
                universityStudent.getUniversityMajorCategory(),
                universityStudent.getUniversityGrade(),
                cutoffDate,
                pageable
        );

        return PageResponse.of(questions.stream().map(RecommendationHighSchoolStudentQuestionResponse::from).toList());
    }
}
