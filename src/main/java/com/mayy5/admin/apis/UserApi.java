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
import org.springframework.web.bind.annotation.RequestParam;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.req.SignUpRequestDto;
import com.mayy5.admin.model.req.UserLoginRequestDto;
import com.mayy5.admin.model.req.UserTokenUpdateRequestDto;
import com.mayy5.admin.model.req.UserUpdateRequestDto;
import com.mayy5.admin.model.res.UserResponseDto;
import com.mayy5.admin.model.res.UserTokenResponseDto;
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
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PostMapping(value = "/v1/login")
	ResponseEntity<UserTokenResponseDto> login(@Valid @RequestBody UserLoginRequestDto loginDTO);

	@ApiOperation(value = "Logout API",
		notes = "Authrization=accessToken 상태에서만 로그아웃 수행")
	@ApiResponses(value = {
		@ApiResponse(code = HttpStatus.SC_NO_CONTENT, message = "성공", response = UserTokenResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping("/v1/logout")
	ResponseEntity logout(@RequestHeader(value = AuthConstant.AUTHORIZATION) String token);

	@ApiOperation(value = "회원 가입 API",
		notes = "메일 인증 서비스와 연계"
			+ ",meta에 Role 부여가 가능하다.")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PostMapping(path = "/v1/signUp", consumes = "application/json")
	ResponseEntity<UserResponseDto> signUp(@Valid @RequestBody SignUpRequestDto signUpRequestDto);

	@ApiOperation(value = "메일 인증 API",
		notes = "메일 인증 서비스")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping(path = "/v1/signUpConfirm")
	String signUpConfirm(@RequestParam("email") String email, @RequestParam("authKey") String authKey);

	@ApiOperation(value = "User List 조회", notes = "전체 유저 리스트를 조회한다.")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping("/v1/users")
	ResponseEntity<List<UserResponseDto>> getUserList();

	@ApiOperation(value = "User Update 수행", notes = "")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PutMapping(path = "/v1/users", consumes = "application/json")
	ResponseEntity<UserResponseDto> updateUser(@Valid @RequestBody UserUpdateRequestDto userUpdateRequestDto);

	@ApiOperation(value = "Token Update API",
		notes = "기존 Token을 받아서 (refresh가 유효할 경우) 새로운 Token으로 갱신한다.")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PutMapping("/v1/token")
	ResponseEntity<UserTokenResponseDto> updateAccessToken(@Valid @RequestBody UserTokenUpdateRequestDto userTokenUpdateRequestDto);

	@ApiOperation(value = "USER Delete API", notes = "")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserTokenResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@DeleteMapping(path = "/v1/users/{id}")
	ResponseEntity<UserResponseDto> deleteUser(@PathVariable("id") String id);
}