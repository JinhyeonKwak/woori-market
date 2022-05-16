package com.mayy5.admin.apis;

import javax.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.req.CommentRequestDto;
import com.mayy5.admin.model.req.PostRequestDto;
import com.mayy5.admin.model.res.CommentResponseDto;
import com.mayy5.admin.model.res.PostPageResponseDto;
import com.mayy5.admin.model.res.PostResponseDto;
import com.mayy5.admin.model.res.UserTokenResponseDto;
import com.mayy5.admin.type.PostType;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Post", tags = SwaggerApiTag.POST)
public interface PostApi {

	@ApiOperation(value = "Post 생성 API",
		notes = "Post 생성 및 저장")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = PostResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PostMapping(path = "/v1/posts", consumes = "application/json")
	ResponseEntity<PostResponseDto> createPost(@Valid @RequestBody PostRequestDto postRequestDto);

	@ApiOperation(value = "Post 조회 API",
		notes = "Post idx를 기반으로 내용 조회")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping("/v1/posts/{idx}")
	ResponseEntity<PostResponseDto> getPost(@Valid @PathVariable(value = "idx") Long idx);

	@ApiOperation(value = "Post Pagination 조회 API",
		notes = "Post Pageable 기반으로 내용 조회")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping("/v1/posts")
	ResponseEntity<Page<PostPageResponseDto>> getPostList(@RequestParam PostType postType,
		@PageableDefault Pageable pageable);

	@ApiOperation(value = "Comment 저장 API",
		notes = "특정 Post에 Comment 저장")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = CommentResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PostMapping("/v1/posts/{idx}/comments")
	ResponseEntity<CommentResponseDto> saveComment(@PathVariable Long idx, @Valid @RequestBody CommentRequestDto dto);

}