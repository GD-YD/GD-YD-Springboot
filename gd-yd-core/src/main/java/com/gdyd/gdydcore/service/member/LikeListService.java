package com.gdyd.gdydcore.service.member;

import com.gdyd.gdydcore.domain.member.LikeList;
import com.gdyd.gdydcore.repository.member.LikeListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class LikeListService {
    private final LikeListRepository likeListRepository;

    public boolean existsByMemberIdAndHighSchoolStudentQuestionId(Long memberId, Long highSchoolStudentQuestionId) {
        return likeListRepository.existsByMemberIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId);
    }

    public boolean existsByMemberIdAndUniversityStudentAnswerId(Long memberId, Long universityStudentAnswerId) {
        return likeListRepository.existsByMemberIdAndUniversityStudentAnswerId(memberId, universityStudentAnswerId);
    }

    public boolean existsByMemberIdAndPostId(Long memberId, Long postId) {
        return likeListRepository.existsByMemberIdAndPostId(memberId, postId);
    }

    public boolean existsByMemberIdAndCommentId(Long memberId, Long commentId) {
        return likeListRepository.existsByMemberIdAndCommentId(memberId, commentId);
    }

    @Transactional
    public void save(LikeList likeList) {
        likeListRepository.save(likeList);
    }

    @Transactional
    public void delete(LikeList likeList) {
        likeListRepository.delete(likeList);
    }
}
