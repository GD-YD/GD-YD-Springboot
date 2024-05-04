package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.member.HighSchoolStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface HighSchoolStudentRepository extends JpaRepository<HighSchoolStudent, Long> {

    Optional<HighSchoolStudent> findByEmail(String email);
}
