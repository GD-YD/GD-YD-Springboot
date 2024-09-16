package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.member.ScrapList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScrapListRepository extends JpaRepository<ScrapList, Long> {
    boolean existsByMemberIdAndHighSchoolStudentQuestionId(Long memberId, Long highSchoolStudentQuestionId);

    boolean existsByMemberIdAndPostId(Long memberId, Long postId);
}
