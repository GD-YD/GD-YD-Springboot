package com.gdyd.gdydcore.service.board;

import com.gdyd.gdydcore.dto.board.PostDto;
import com.gdyd.gdydcore.repository.board.PostRepository;
import com.gdyd.gdydcore.domain.board.Post;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PostService {
    @Autowired
    private final PostRepository postRepository;

    public void postWrite(PostDto postDto) throws IOException {
        Post post = postDto.toDomain();
        post.setCreatedAt(LocalDateTime.now());

        postRepository.save(post);
    }
}
