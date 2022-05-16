package com.mayy5.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.Comment;
import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.repository.CommentRepository;
import com.mayy5.admin.repository.PostRepository;
import com.mayy5.admin.repository.UserRepository;
import com.mayy5.admin.type.PostType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final UserRepository userRepository;

	public Page<Post> findPostList(PostType postType, Pageable pageable) {
		return postRepository.findAllByPostType(postType, pageable);
	}

	public Post findPostById(Long id) {
		return postRepository.findById(id).orElse(new Post());
	}

	@Transactional
	public Comment saveComment(String userId, Long postId, Comment input) {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, userId));
		Post post = postRepository.findById(postId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, "POST - " + postId));
		input.setPost(post);
		input.setUser(user);
		return commentRepository.save(input);
	}

	@Transactional
	public Post savePost(String userId, Post input) {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, userId));
		input.setUser(user);
		return postRepository.save(input);
	}

}
