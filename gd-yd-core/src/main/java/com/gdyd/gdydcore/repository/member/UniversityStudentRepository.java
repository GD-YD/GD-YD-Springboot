package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.member.UniversityStudent;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UniversityStudentRepository extends JpaRepository<UniversityStudent, Long> {
    Optional<UniversityStudent> findByEmail(String email);
}
