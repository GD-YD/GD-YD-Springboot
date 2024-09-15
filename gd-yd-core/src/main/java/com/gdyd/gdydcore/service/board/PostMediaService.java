package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.domain.board.PostMedia;
import com.gdyd.gdydcore.repository.board.PostMediaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostMediaService {
    private final PostMediaRepository postMediaRepository;

    @Transactional
    public void saveAllPostMedia(Iterable<PostMedia> postMedias) {
        postMediaRepository.saveAll(postMedias);
    }

    @Transactional
    public void deleteAllPostMedia(Iterable<PostMedia> postMedias) {
        postMediaRepository.deleteAll(postMedias);
    }
}
