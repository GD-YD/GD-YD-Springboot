package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.repository.board;
import com.gdyd.gdydcore.domain.board;
import com.gdyd.gdydapi.dto.board;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    public void postWrite(PostDto postDto) throws IOException {
        Post post = postDto.toEntity();
        post.setCreatedAt(LocalDateTime.now());

        PostRepository.save(post);
    }
}
