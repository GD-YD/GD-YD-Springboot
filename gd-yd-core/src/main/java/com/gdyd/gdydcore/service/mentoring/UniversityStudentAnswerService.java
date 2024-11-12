package com.gdyd.gdydcore.service.mentoring;

import com.gdyd.gdydcore.domain.mentoring.UniversityStudentAnswer;
import com.gdyd.gdydcore.repository.mentoring.UniversityStudentAnswerRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityStudentAnswerService {
    private final UniversityStudentAnswerRepository universityStudentAnswerRepository;

    public UniversityStudentAnswer getUniversityStudentAnswerById(Long id) {
        return universityStudentAnswerRepository.findById(id).orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_UNIVERSITY_STUDENT_ANSWER));
    }

    @Transactional
    public void save(UniversityStudentAnswer universityStudentAnswer) {
        universityStudentAnswerRepository.save(universityStudentAnswer);
    }

    @Transactional
    public void delete(UniversityStudentAnswer universityStudentAnswer) {
        universityStudentAnswerRepository.delete(universityStudentAnswer);
    }
}
