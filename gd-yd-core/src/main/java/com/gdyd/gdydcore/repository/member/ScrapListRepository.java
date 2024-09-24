package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.member.ScrapList;
import com.gdyd.gdydcore.domain.member.ScrapType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapListRepository extends JpaRepository<ScrapList, Long> {
    boolean existsByMemberIdAndHighSchoolStudentQuestionId(Long memberId, Long highSchoolStudentQuestionId);

    boolean existsByMemberIdAndPostId(Long memberId, Long postId);

    Page<ScrapList> findByMemberIdAndType(Long memberId, ScrapType type, Pageable pageable);
}
