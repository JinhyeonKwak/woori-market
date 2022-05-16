package com.mayy5.admin.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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

		pageable = PageRequest.of(pageable.getPageNumber() <= 0 ? 0 : pageable.getPageNumber() - 1,
			pageable.getPageSize(),pageable.getSort());

		return postRepository.findAllByPostType(postType, pageable);
	}

	public Post findPostByIdx(Long idx) {
		return postRepository.findById(idx).orElse(new Post());
	}

	@Transactional
	public Comment saveComment(String userId, Long postIdx, Comment input) {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, userId));
		Post post = postRepository.findById(postIdx).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, "POST - " + postIdx));
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
