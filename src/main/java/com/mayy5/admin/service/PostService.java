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
import com.mayy5.admin.util.EntityUtil;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RequiredArgsConstructor
@Service
public class PostService {

	private final PostRepository postRepository;
	private final CommentRepository commentRepository;
	private final UserService userService;
	private final UserRepository userRepository;

	public Page<Post> findPostList(PostType postType, Pageable pageable) {
		return postRepository.findAllByPostType(postType, pageable);
	}

	public Post findPostById(Long id) {
		return postRepository.findById(id).orElse(new Post());
	}

	@Transactional
	public Comment updateComment(String userId, Long commentId, Comment input) {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, userId));
		Comment comment = commentRepository.findById(commentId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, "Comment"));

		if (userService.isValidUser(user, comment.getUser())) {
			return commentRepository.save(EntityUtil.setValueExceptNull(comment,input));
		} else {
			throw new CommonException(BError.NOT_MATCH, "User");
		}
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
	public void deleteComment(String userId, Long commentId) throws CommonException {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, userId));

		commentRepository.findById(commentId).ifPresent(comment -> {
			if (userService.isValidUser(user, comment.getUser())) {
				commentRepository.deleteById(commentId);
			} else {
				throw new CommonException(BError.NOT_MATCH, "User");
			}
		});
		return;
	}

	@Transactional
	public Post savePost(String userId, Post input) {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, userId));
		input.setUser(user);
		return postRepository.save(input);
	}


	@Transactional
	public void deletePost(String userId, Long postId) throws CommonException {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, userId));
		postRepository.findById(postId).ifPresent(comment -> {
			if (userService.isValidUser(user, comment.getUser())) {
				postRepository.deleteById(postId);
			} else {
				throw new CommonException(BError.NOT_MATCH, "User");
			}
		});
		return;
	}

	@Transactional
	public Post updatePost(String userId, Long postId, Post input) {
		User user = userRepository.findById(userId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, userId));
		Post post = postRepository.findById(postId).orElseThrow(() ->
			new CommonException(BError.NOT_EXIST, "POST"));
		if (userService.isValidUser(user, post.getUser())) {
			return postRepository.save(EntityUtil.setValueExceptNull(post, input));
		} else {
			throw new CommonException(BError.NOT_MATCH, "User");
		}
	}

}
