package com.gdyd.gdydcore.repository.member;

import com.gdyd.gdydcore.domain.member.LikeList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LikeListRepository extends JpaRepository<LikeList, Long> {

    boolean existsByMemberIdAndHighSchoolStudentQuestionId(Long memberId, Long highSchoolStudentQuestionId);

    boolean existsByMemberIdAndUniversityStudentAnswerId(Long memberId, Long universityStudentAnswerId);

    boolean existsByMemberIdAndPostId(Long memberId, Long postId);

    boolean existsByMemberIdAndCommentId(Long memberId, Long commentId);
}
