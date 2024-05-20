package com.gdyd.gdydcore.service.member;

import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import com.gdyd.gdydcore.repository.member.HighSchoolStudentRepository;
import com.gdyd.gdydsupport.exception.BusinessException;
import com.gdyd.gdydsupport.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class HighSchoolStudentService {
    private final HighSchoolStudentRepository highSchoolStudentRepository;

    public HighSchoolStudent getHighSchoolStudentByMemberId(Long memberId) {
        return highSchoolStudentRepository.findById(memberId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_MEMBER));
    }

    public boolean existsHighSchoolStudentByEmail(String email) {
        return highSchoolStudentRepository.findByEmail(email).isPresent();
    }

    @Transactional
    public void save(HighSchoolStudent highSchoolStudent) {
        highSchoolStudentRepository.save(highSchoolStudent);
    }
}
