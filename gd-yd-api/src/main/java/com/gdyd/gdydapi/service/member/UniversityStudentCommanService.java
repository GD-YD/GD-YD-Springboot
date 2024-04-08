package com.gdyd.gdydapi.service.member;

import com.gdyd.gdydcore.service.member.UniversityStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class UniversityStudentCommanService {

    private final UniversityStudentService universityStudentService;


}
