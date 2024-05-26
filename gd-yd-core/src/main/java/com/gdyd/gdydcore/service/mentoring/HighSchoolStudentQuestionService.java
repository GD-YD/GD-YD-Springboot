package com.gdyd.gdydcore.service.mentoring;

import com.gdyd.gdydcore.repository.mentoring.HighSchoolStudentQuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HighSchoolStudentQuestionService {
    private final HighSchoolStudentQuestionRepository highSchoolStudentQuestionRepository;
}
