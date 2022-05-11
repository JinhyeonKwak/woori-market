package com.mayy5.admin.apis;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.res.DemosResponseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

/**
 *	데모 API 생성
 *
 * @author @kooru
 * @see com.mayy5.admin.controller.DemoController
 */
@Api(value = "Demo", tags = SwaggerApiTag.DEMO)
@Validated
public interface DemoApi {
	@ApiOperation(value = "유저의 데모 조회 API", notes = "")
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_OK, message = "성공", response = DemosResponseDto.class),
		@ApiResponse(code = HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping(value = "/v1/users/{userId}/demo")
	ResponseEntity<DemosResponseDto> getDemos(
		@ApiParam(required = true) String userId);

}