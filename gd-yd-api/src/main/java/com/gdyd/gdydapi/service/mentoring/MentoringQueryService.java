package com.gdyd.gdydapi.service.mentoring;

import com.gdyd.gdydapi.response.common.PageResponse;
import com.gdyd.gdydapi.response.mentoring.DetailHighSchoolStudentQuestionResponse;
import com.gdyd.gdydapi.response.mentoring.HighSchoolStudentQuestionResponse;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import com.gdyd.gdydcore.service.mentoring.HighSchoolStudentQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
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

    /**
     * 고등학생 질문 목록 조회 (페이지네이션)
     * @param pageable 페이지 네이션 정보
     */
    public PageResponse<HighSchoolStudentQuestionResponse> getHighSchoolStudentQuestions(Pageable pageable) {
        Page<HighSchoolStudentQuestion> pages = highSchoolStudentQuestionService.findHighSchoolStudentQuestionByPagination(pageable);
        return PageResponse.of(pages.getContent().stream().map(HighSchoolStudentQuestionResponse::from).toList());
    }

    public PageResponse<HighSchoolStudentQuestionResponse> getBestHighSchoolStudentQuestions(LocalDateTime weeksAgo, Pageable pageable) {
        List<HighSchoolStudentQuestion> questions = highSchoolStudentQuestionService.findAllByCreatedAtIsAfter(weeksAgo, pageable);
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
}
