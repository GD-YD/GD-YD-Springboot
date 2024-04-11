package com.gdyd.gdydcore.repository.board;

import com.gdyd.gdydcore.domain.board.Post;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {

}
