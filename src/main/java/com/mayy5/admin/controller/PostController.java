package com.mayy5.admin.controller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import com.mayy5.admin.apis.PostApi;
import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.Comment;
import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.mapper.CommentMapper;
import com.mayy5.admin.model.mapper.PostMapper;
import com.mayy5.admin.model.req.CommentRequestDto;
import com.mayy5.admin.model.req.PostRequestDto;
import com.mayy5.admin.model.res.CommentResponseDto;
import com.mayy5.admin.model.res.PostPageResponseDto;
import com.mayy5.admin.model.res.PostResponseDto;
import com.mayy5.admin.service.PostService;
import com.mayy5.admin.service.UserService;
import com.mayy5.admin.type.PostType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
public class PostController implements PostApi {

	private final UserService userService;
	private final PostService postService;
	private final PostMapper postMapper;
	private final CommentMapper commentMapper;

	@Override
	public ResponseEntity<PostResponseDto> createPost(PostRequestDto postRequestDto) {
		try {
			Post input = postMapper.toEntity(postRequestDto);
			Post post = postService.savePost(userService.getLoginUserId(), input);
			return new ResponseEntity<>(postMapper.toDto(post), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "createPost");
		}
	}

	@Override
	public ResponseEntity<PostResponseDto> getPost(Long id) {
		try {
			Post post = postService.findPostById(id);
			return new ResponseEntity<>(postMapper.toDto(post), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "createPost");
		}
	}

	@Override
	public ResponseEntity<Page<PostPageResponseDto>> getPostList(PostType postType, Pageable pageable) {
		try {
			Page<Post> posts = postService.findPostList(postType, pageable);
			return new ResponseEntity<>(posts.map(post -> postMapper.toPageDto(post)), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "getPostList");
		}
	}

	@Override
	public ResponseEntity<CommentResponseDto> saveComment(Long id, CommentRequestDto dto) {
		try {
			String userId = userService.getLoginUserId();
			Comment input = commentMapper.toEntity(dto);
			Comment comment = postService.saveComment(userId, id, input);
			return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "Save Comment");
		}
	}

	@Override
	public ResponseEntity<CommentResponseDto> updateComment(Long id, CommentRequestDto dto) {
		try {
			String userId = userService.getLoginUserId();
			Comment input = commentMapper.toEntity(dto);
			Comment comment = postService.updateComment(userId, id, input);
			return new ResponseEntity<>(commentMapper.toDto(comment), HttpStatus.OK);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "Update Comment");
		}
	}

	@Override
	public ResponseEntity deleteComment(Long id) {
		try {
			String userId = userService.getLoginUserId();
			postService.deleteComment(userId, id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "Delete Comment");
		}
	}
}