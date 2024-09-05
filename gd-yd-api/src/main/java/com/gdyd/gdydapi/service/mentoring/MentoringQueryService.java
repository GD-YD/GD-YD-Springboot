package com.gdyd.gdydapi.service.mentoring;

import com.gdyd.gdydapi.response.common.PageResponse;
import com.gdyd.gdydapi.response.mentoring.HighSchoolStudentQuestionResponse;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.service.mentoring.HighSchoolStudentQuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MentoringQueryService {
    private final HighSchoolStudentQuestionService highSchoolStudentQuestionService;

    /**
     * 고등학생 질문 목록 조회 (페이지네이션)
     * @param pageable
     */
    public PageResponse<HighSchoolStudentQuestionResponse> getHighSchoolStudentQuestions(Pageable pageable) {
        Page<HighSchoolStudentQuestion> pages = highSchoolStudentQuestionService.findHighSchoolStudentQuestionByPagination(pageable);
        return PageResponse.of(pages.getContent().stream().map(HighSchoolStudentQuestionResponse::from).toList());
    }
}
