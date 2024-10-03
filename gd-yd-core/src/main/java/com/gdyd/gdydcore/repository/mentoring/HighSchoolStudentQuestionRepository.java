package com.gdyd.gdydcore.repository.mentoring;

import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

public interface HighSchoolStudentQuestionRepository extends JpaRepository<HighSchoolStudentQuestion, Long> {
    List<HighSchoolStudentQuestion> findByLikeCountGreaterThanEqualAndCreatedAtAfter(int like, LocalDateTime weeksAgo, Pageable pageable);
}
