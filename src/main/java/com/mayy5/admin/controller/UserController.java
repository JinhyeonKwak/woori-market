package com.mayy5.admin.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import com.mayy5.admin.apis.UserApi;
import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.model.mapper.UserMapper;
import com.mayy5.admin.model.req.SignUpRequestDto;
import com.mayy5.admin.model.req.UserLoginRequestDto;
import com.mayy5.admin.model.req.UserTokenUpdateRequestDto;
import com.mayy5.admin.model.req.UserUpdateRequestDto;
import com.mayy5.admin.model.res.UserResponseDto;
import com.mayy5.admin.model.res.UserTokenResponseDto;
import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.service.MailSendService;
import com.mayy5.admin.service.TokenService;
import com.mayy5.admin.service.UserService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class UserController implements UserApi {

	private final TokenService tokenService;
	private final UserService userService;
	private final MailSendService mailSendService;
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<UserTokenResponseDto> login(UserLoginRequestDto loginDTO) {

		try {
			User user = userService.getUser(loginDTO.getId());
			if (!user.isValid()) {
				log.error("인증되지 않은 사용자 입니다. - {}", user.getId());
				throw new CommonException(BError.NOT_SUPPORT, user.getId());
			}
			// 패스워드 검증
			if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
				throw new CommonException(BError.NOT_MATCH, "Password");
			} else {
				return new ResponseEntity<>(
					UserTokenResponseDto.builder()
						.accessToken(tokenService.genAccessToken(loginDTO.getId()))
						.refreshToken(tokenService.genRefreshToken(loginDTO.getId()))
						.meta(user.getMeta())
						.build(),
					HttpStatus.OK);
			}
		} catch (CommonException e) {
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "Login");
		}
	}

	public ResponseEntity logout(String accessToken) {
		SecurityContextHolder.clearContext();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<UserResponseDto> signUp(SignUpRequestDto signUpRequestDto) {
		try {
			User input = userMapper.toEntity(signUpRequestDto);
			User user = userService.signUp(input);
			mailSendService.sendAuthMail(user.getEmail());
			return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
		} catch (CommonException e) {
			log.error(e.getMessage());
			log.debug(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(e.getMessage(), e);
			throw e;
		}
	}

	public ResponseEntity<List<UserResponseDto>> getUserList() {

		List<User> userList = userService.getUserList();
		return new ResponseEntity<>(userList.stream()
			.map(user -> userMapper.toDto(user))
			.collect(Collectors.toList()), HttpStatus.OK);
	}

	public ResponseEntity<UserResponseDto> updateUser(UserUpdateRequestDto userUpdateRequestDto)
		throws CommonException {

		try {
			log.debug("updateUser {}", userUpdateRequestDto);

			User input = userMapper.toEntity(userUpdateRequestDto);
			String email = input.getEmail();
			// 패스워드 업데이트 로직
			Optional.ofNullable(userUpdateRequestDto.getNewPassword()).ifPresent(s -> {
				String oldPassword = userService.getUser(email).getPassword();
				// 패스워드 검증
				if (!passwordEncoder.matches(userUpdateRequestDto.getPassword(), oldPassword)) {
					throw new CommonException(BError.NOT_MATCH, "Password");
				}
				// 기존 패스워드와 새로운 패스워드 검증
				if (userUpdateRequestDto.getNewPassword().equals(userUpdateRequestDto.getPassword()))
					throw new CommonException(BError.MATCHS, "old Password", "new Password");

				input.setPassword(passwordEncoder.encode(s));
			});

			User user = userService.updateUser(input);
			return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
		} catch (CommonException e) {
			log.error(e.getMessage());
			log.debug(e.getMessage(), e);
			throw e;
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(e.getMessage(), e);
			throw e;
		}
	}

	public ResponseEntity<UserTokenResponseDto> updateAccessToken(UserTokenUpdateRequestDto userTokenUpdateRequestDto) {
		try {
			log.debug("TokenUpdate {}", userTokenUpdateRequestDto);
			return new ResponseEntity<>(
				UserTokenResponseDto.builder()
					.accessToken(tokenService.genAccessTokenByRefreshToken(userTokenUpdateRequestDto.getRefreshToken()))
					.refreshToken(userTokenUpdateRequestDto.getRefreshToken())
					.build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "Token Update");
		}
	}

	public ResponseEntity<UserResponseDto> deleteUser(String id) {
		try {
			if (id.equals(AuthConstant.ADMIN_USER))
				throw new CommonException(BError.NOT_SUPPORT, "Delete Admin User");
			Optional.ofNullable(userService.getUser(id))
				.orElseThrow(() -> new CommonException(BError.NOT_EXIST, "ID"));
			userService.deleteUser(id);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		} catch (Exception e) {
			log.error("Delete User Fail {}", e.getMessage());
			log.debug(e.getMessage(), e);
			throw e;
		}
	}

	@Override
	public String signUpConfirm(String email, String authKey) {
		log.info("Email {}, AuthKey {}", email, authKey);
		try {
			tokenService.validateToken(authKey);
			userService.updateAuthStatus(email);
		} catch (Exception e) {
			log.error("signUpConfirm Fail {}", e.getMessage());
			log.debug(e.getMessage(), e);
			throw e;
		}
		return "가입을 축하합니다.";
	}
}