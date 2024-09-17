package com.gdyd.gdydcore.service.member;

import com.gdyd.gdydcore.domain.report.Report;
import com.gdyd.gdydcore.repository.member.ReportRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReportService {
    private final ReportRepository reportRepository;

    public boolean existsByMemberIdAndPostId(Long memberId, Long postId) {
        return reportRepository.existsByReporterIdAndPostId(memberId, postId);
    }

    public boolean existsByMemberIdAndCommentId(Long memberId, Long commentId) {
        return reportRepository.existsByReporterIdAndCommentId(memberId, commentId);
    }

    public boolean existsByMemberIdAndHighSchoolStudentQuestionId(Long memberId, Long highSchoolStudentQuestionId) {
        return reportRepository.existsByReporterIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId);
    }

    public boolean existsByMemberIdAndUniversityStudentAnswerId(Long memberId, Long universityStudentAnswerId) {
        return reportRepository.existsByReporterIdAndUniversityStudentAnswerId(memberId, universityStudentAnswerId);
    }

    @Transactional
    public void save(Report repost) {
        reportRepository.save(repost);
    }
}
