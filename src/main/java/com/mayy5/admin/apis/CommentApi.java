package com.mayy5.admin.apis;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.res.UserTokenResponseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Board", tags = SwaggerApiTag.POST)
public interface CommentApi {


    @ApiOperation(value = "Board 조회 API",
        notes = "Board idx를 기반으로 내용 조회")
    @ApiResponses(value = {
        @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenResponseDto.class),
        @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
        @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping("/v1/boards/{idx}")
	Post createComment(@PathVariable(value="idx") Long idx);
    
    @GetMapping("/v1/boards")
    Page<Post> getBoardList(@PageableDefault Pageable pageable);
}