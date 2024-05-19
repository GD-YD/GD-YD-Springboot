package com.gdyd.gdydapi.controller.member;

import com.gdyd.gdydcore.service.member.UniversityStudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/university-student")
public class UniversityStudentController {
    private final UniversityStudentService universityStudentService;
}
