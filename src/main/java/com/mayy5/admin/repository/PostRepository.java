package com.mayy5.admin.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.type.PostType;

public interface PostRepository extends JpaRepository<Post, Long> {
	Post findByUser(User user);

	Page<Post> findAllByPostType(PostType postType, Pageable pageable);
}