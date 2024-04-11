package com.gdyd.gdydcore.repository.board;

import com.gdyd.gdydcore.domain.board.Comment;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}
