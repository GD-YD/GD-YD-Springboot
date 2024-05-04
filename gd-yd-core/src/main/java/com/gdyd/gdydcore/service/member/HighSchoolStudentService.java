package com.gdyd.gdydcore.service.member;

import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.repository.member.HighSchoolStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HighSchoolStudentService {
    private final HighSchoolStudentRepository highSchoolStudentRepository;

    public boolean existsHighSchoolStudentByEmail(String email) {
        return highSchoolStudentRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void save(HighSchoolStudent highSchoolStudent) {
        highSchoolStudentRepository.save(highSchoolStudent);
    }
}
