package com.gdyd.gdydcore.repository.mentoring;

import com.gdyd.gdydcore.domain.member.Grade;
import com.gdyd.gdydcore.domain.member.UniversityMajorCategory;
import com.gdyd.gdydcore.domain.mentoring.HighSchoolStudentQuestion;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface HighSchoolStudentQuestionRepository extends JpaRepository<HighSchoolStudentQuestion, Long> {
    List<HighSchoolStudentQuestion> findAllByCreatedAtIsAfter(LocalDateTime weeksAgo, Pageable pageable);

    @Query("SELECT q FROM HighSchoolStudentQuestion q " +
            "WHERE q.createdAt > :cutoffDate " +
            "ORDER BY (" +
            "CASE WHEN q.universityNameTag = :universityNameTag THEN 0.3 ELSE 0 END + " +
            "CASE WHEN q.universityMajorTag = :universityMajorTag THEN 0.6 ELSE 0 END + " +
            "CASE WHEN q.universityGradeTag = :universityGradeTag THEN 0.1 ELSE 0 END" +
            ") DESC")
    List<HighSchoolStudentQuestion> findTopQuestionsByTag(
            @Param("universityNameTag") String universityNameTag,
            @Param("universityMajorTag") UniversityMajorCategory universityMajorTag,
            @Param("universityGradeTag") Grade universityGradeTag,
            @Param("cutoffDate") LocalDateTime cutoffDate,
            Pageable pageable);

    List<HighSchoolStudentQuestion> findByLikeCountGreaterThanEqualAndCreatedAtAfter(Long like, LocalDateTime weeksAgo, Pageable pageable);
}
