package com.gdyd.gdydcore.repository.board;

import com.gdyd.gdydcore.domain.board.Post;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndMemberId(Long postId, Long memberId);

    List<Post> findByLikeCountGreaterThanEqualAndCreatedAtAfter(int like, LocalDateTime weeksAgo, Pageable pageable);
}
