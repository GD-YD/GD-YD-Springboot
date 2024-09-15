package com.gdyd.gdydcore.service.mentoring;

import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestionMedia;
import com.gdyd.gdydcore.repository.mentoring.HighSchoolStudentQuestionMediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HighSchoolStudentQuestionMediaService {
    private final HighSchoolStudentQuestionMediaRepository highSchoolStudentQuestionMediaRepository;

    @Transactional
    public void saveAllHighSchoolStudentQuestionMedia(Iterable<HighSchoolStudentQuestionMedia> highSchoolStudentQuestionMedias) {
        highSchoolStudentQuestionMediaRepository.saveAll(highSchoolStudentQuestionMedias);
    }

    @Transactional
    public void deleteAllHighSchoolStudentQuestionMedia(Iterable<HighSchoolStudentQuestionMedia> highSchoolStudentQuestionMedias) {
        highSchoolStudentQuestionMediaRepository.deleteAll(highSchoolStudentQuestionMedias);
    }
}
