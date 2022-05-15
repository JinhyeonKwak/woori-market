package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<Post, Long> {
    Post findByUser(User user);
}