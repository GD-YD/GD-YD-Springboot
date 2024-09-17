package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.report.Report;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<Report, Long> {

    boolean existsByReporterIdAndPostId(Long reporterId, Long postId);

    boolean existsByReporterIdAndCommentId(Long reporterId, Long commentId);

    boolean existsByReporterIdAndHighSchoolStudentQuestionId(Long reporterId, Long highSchoolStudentQuestionId);

    boolean existsByReporterIdAndUniversityStudentAnswerId(Long reporterId, Long universityStudentAnswerId);
}
