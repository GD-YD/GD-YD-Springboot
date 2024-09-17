package com.gdyd.gdydcore.service.member;

import com.gdyd.gdydcore.domain.member.ScrapList;
import com.gdyd.gdydcore.repository.member.ScrapListRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ScrapListService {
    private final ScrapListRepository scrapListRepository;

    public boolean existsByMemberIdAndHighSchoolStudentQuestionId(Long memberId, Long highSchoolStudentQuestionId) {
        return scrapListRepository.existsByMemberIdAndHighSchoolStudentQuestionId(memberId, highSchoolStudentQuestionId);
    }

    public boolean existsByMemberIdAndPostId(Long memberId, Long postId) {
        return scrapListRepository.existsByMemberIdAndPostId(memberId, postId);
    }

    @Transactional
    public void save(ScrapList scrapList) {
        scrapListRepository.save(scrapList);
    }

    @Transactional
    public void delete(ScrapList scrapList) {
        scrapListRepository.delete(scrapList);
    }
}
