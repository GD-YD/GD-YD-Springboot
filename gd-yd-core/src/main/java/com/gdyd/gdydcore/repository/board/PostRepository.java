package com.gdyd.gdydcore.repository.board;

import com.gdyd.gdydcore.domain.board.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface PostRepository extends JpaRepository<Post, Long> {
    Optional<Post> findByIdAndMemberId(Long postId, Long memberId);

    List<Post> findByLikeCountGreaterThanEqualAndCreatedAtAfter(Long like, LocalDateTime weeksAgo, Pageable pageable);

    @Query("""
        SELECT p FROM Post p
        WHERE p.title LIKE %:keyword% OR p.content LIKE %:keyword%
        ORDER BY
            CASE WHEN p.title LIKE %:keyword% THEN 0 ELSE 1 END,
            p.createdAt DESC
    """)
    Page<Post> findByKeywordWithPriority(@Param("keyword") String keyword, Pageable pageable);
}
