package com.gdyd.gdydcore.service.mentoring;

import com.gdyd.gdydcore.domain.member.Grade;
import com.gdyd.gdydcore.domain.member.UniversityMajorCategory;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import com.gdyd.gdydcore.repository.mentoring.HighSchoolStudentQuestionRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
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
public class HighSchoolStudentQuestionService {
    private final HighSchoolStudentQuestionRepository highSchoolStudentQuestionRepository;

    public HighSchoolStudentQuestion getHighSchoolStudentQuestionById(Long highSchoolStudentQuestionId) {
        return highSchoolStudentQuestionRepository.findById(highSchoolStudentQuestionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_HIGH_SCHOOL_STUDENT_QUESTION));
    }

    public Page<HighSchoolStudentQuestion> findHighSchoolStudentQuestionByPagination(Pageable pageable) {
        return highSchoolStudentQuestionRepository.findAll(pageable);
    }

    public List<HighSchoolStudentQuestion> findByLikeCountGreaterThanEqualAndCreatedAtAfter(Long like, LocalDateTime weeksAgo, Pageable pageable) {
        return highSchoolStudentQuestionRepository.findByLikeCountGreaterThanEqualAndCreatedAtAfter(like, weeksAgo, pageable);
    }

    public List<HighSchoolStudentQuestion> findTopQuestionsByScore(String universityNameTag, UniversityMajorCategory universityMajorTag, Grade universityGradeTag, LocalDateTime cutoffDate, Pageable pageable) {
        return highSchoolStudentQuestionRepository.findTopQuestionsByTag(universityNameTag, universityMajorTag, universityGradeTag, cutoffDate, pageable);
    }

    @Transactional
    public void save(HighSchoolStudentQuestion highSchoolStudentQuestion) {
        highSchoolStudentQuestionRepository.save(highSchoolStudentQuestion);
    }
}
