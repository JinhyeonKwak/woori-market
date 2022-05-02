package com.mayy5.admin.apis;

import java.util.List;

import javax.validation.Valid;

import org.apache.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.req.UserCreateRTO;
import com.mayy5.admin.model.req.UserLoginRTO;
import com.mayy5.admin.model.req.UserTokenUpdateRTO;
import com.mayy5.admin.model.req.UserUpdateRTO;
import com.mayy5.admin.model.res.UserRTO;
import com.mayy5.admin.model.res.UserTokenRTO;
import com.mayy5.admin.security.AuthConstant;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;


/**
 * User API 생성
 * token 생성 및 업데이트, user crud를 다룬다.
 * @author @kooru
 * @see com.mayy5.admin.controller.UserController
 */
@Api(value = "User", tags = SwaggerApiTag.USER)
@Validated
public interface UserApi {

	@ApiOperation(value = "Login API",
		notes = "Id와 Password를 Post로 전달하며 성공시 Token 객체 { accessToken ,refreshToken }을 전달 받는다."
			+ " 이를 Header Authorization: Bearer + accessToken 형태로 전달해야 API의 토큰 처리 수행이 진행된다.")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenRTO.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PostMapping(value = "/v1/login")
	ResponseEntity<UserTokenRTO> login(@RequestBody UserLoginRTO loginDTO);

	@ApiOperation(value = "Logout API",
		notes = "Authrization=accessToken 상태에서만 로그아웃 수행")
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_NO_CONTENT, message = "성공", response = UserTokenRTO.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping("/v1/logout")
	ResponseEntity logout(@RequestHeader(value = AuthConstant.AUTHORIZATION) String token);

	@ApiOperation(value = "Create User API",
		notes = "meta에 Role 부여가 가능하다.")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserRTO.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PostMapping(path = "/v1/users", consumes = "application/json")
	ResponseEntity<UserRTO> createUser(@RequestBody @Valid UserCreateRTO userCreateRTO);

	@ApiOperation(value = "User List 조회", notes = "전체 유저 리스트를 조회한다.")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenRTO.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping("/v1/users")
	ResponseEntity<List<UserRTO>> getUserList();

	@ApiOperation(value = "User Update 수행", notes = "")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenRTO.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PutMapping(path = "/v1/users", consumes = "application/json")
	ResponseEntity<UserRTO> updateUser(@Valid @RequestBody UserUpdateRTO userUpdateRTO);

	@ApiOperation(value = "Token Update API",
		notes = "기존 Token을 받아서 (refresh가 유효할 경우) 새로운 Token으로 갱신한다.")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenRTO.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PutMapping("/v1/token")
	ResponseEntity<UserTokenRTO> updateAccessToken(@Valid @RequestBody UserTokenUpdateRTO userTokenUpdateRTO);

	@ApiOperation(value = "USER Delete API", notes = "")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenRTO.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@DeleteMapping(path = "/v1/users/{id}")
	ResponseEntity<UserRTO> deleteUser(@PathVariable("id") String id);
}