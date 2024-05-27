package com.gdyd.gdydcore.service.mentoring;

import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.repository.mentoring.HighSchoolStudentQuestionRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HighSchoolStudentQuestionService {
    private final HighSchoolStudentQuestionRepository highSchoolStudentQuestionRepository;

    public HighSchoolStudentQuestion getHighSchoolStudentQuestionById(Long highSchoolStudentQuestionId) {
        return highSchoolStudentQuestionRepository.findById(highSchoolStudentQuestionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_HIGH_SCHOOL_STUDENT_QUESTION));
    }

    @Transactional
    public void save(HighSchoolStudentQuestion highSchoolStudentQuestion) {
        highSchoolStudentQuestionRepository.save(highSchoolStudentQuestion);
    }
}
