package com.mayy5.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.mayy5.admin.model.domain.Comment;

public interface CommentRepository extends JpaRepository<Comment, Long> {

}


