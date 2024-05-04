package com.gdyd.gdydcore.service.member;

import com.gdyd.gdydcore.domain.member.UniversityStudent;
import com.gdyd.gdydcore.repository.member.UniversityStudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class UniversityStudentService {

    private final UniversityStudentRepository repository;

    public boolean existsUniversityStudentByEmail(String email) {
        return repository.findByEmail(email).isPresent();
    }

    @Transactional
    public void save(UniversityStudent universityStudent) {
        repository.save(universityStudent);
    }

}
