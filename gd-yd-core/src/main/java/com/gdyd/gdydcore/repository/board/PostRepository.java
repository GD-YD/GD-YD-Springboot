package com.gdyd.gdydcore.repository.board;

import com.gdyd.gdydcore.domain.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndMemberId(Long postId, Long memberId);
}
