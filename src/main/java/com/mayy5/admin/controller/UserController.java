package com.mayy5.admin.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RestController;

import com.mayy5.admin.apis.UserApi;
import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.mapper.UserMapper;
import com.mayy5.admin.model.req.UserCreateRTO;
import com.mayy5.admin.model.req.UserLoginRTO;
import com.mayy5.admin.model.req.UserTokenUpdateRTO;
import com.mayy5.admin.model.req.UserUpdateRTO;
import com.mayy5.admin.model.res.UserRTO;
import com.mayy5.admin.model.res.UserTokenRTO;
import com.mayy5.admin.security.AuthConstant;
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
	private final UserMapper userMapper;
	private final PasswordEncoder passwordEncoder;

	public ResponseEntity<UserTokenRTO> login(@RequestBody UserLoginRTO loginDTO) {

		try {
			User user = userService.getUser(loginDTO.getId());
			// 패스워드 검증
			if (!passwordEncoder.matches(loginDTO.getPassword(), user.getPassword())) {
				throw new CommonException(BError.NOT_MATCH, "Password");
			} else {
				String accessToken = tokenService.genAccessToken(loginDTO.getId());
				return new ResponseEntity<>(
					UserTokenRTO.builder()
						.accessToken(accessToken)
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

	public ResponseEntity logout(@RequestHeader(value = AuthConstant.AUTHORIZATION) String accessToken) {
		SecurityContextHolder.clearContext();
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	public ResponseEntity<UserRTO> createUser(@RequestBody @Valid UserCreateRTO userCreateRTO) {
		try {
			if (userCreateRTO.getId().trim().isEmpty())
				throw new CommonException(BError.FAIL_REASON, "User Create", "ID is Not Valid");
			User input = userMapper.toEntity(userCreateRTO);
			User user = userService.createUser(input);
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

	public ResponseEntity<List<UserRTO>> getUserList() {
		List<User> userList = userService.getUserList();
		return new ResponseEntity<>(userList.stream()
			.map(user -> userMapper.toDto(user))
			.collect(Collectors.toList()), HttpStatus.OK);
	}

	public ResponseEntity<UserRTO> updateUser(@Valid @RequestBody UserUpdateRTO userUpdateRTO)
		throws CommonException {

		try {
			log.debug("updateUser {}", userUpdateRTO);

			User input = userMapper.toEntity(userUpdateRTO);
			String id = input.getId();
			// 패스워드 업데이트 로직
			Optional.ofNullable(userUpdateRTO.getNewPassword()).ifPresent(s -> {
				String oldPassword = userService.getUser(id).getPassword();
				// 패스워드 검증
				if (!passwordEncoder.matches(userUpdateRTO.getPassword(), oldPassword)) {
					throw new CommonException(BError.NOT_MATCH, "Password");
				}
				// 기존 패스워드와 새로운 패스워드 검증
				if (userUpdateRTO.getNewPassword().equals(userUpdateRTO.getPassword()))
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

	public ResponseEntity<UserTokenRTO> updateAccessToken(@Valid @RequestBody UserTokenUpdateRTO userTokenUpdateRTO) {
		try {
			log.debug("TokenUpdate {}", userTokenUpdateRTO);
			return new ResponseEntity<>(
				UserTokenRTO.builder()
					.accessToken(tokenService.genAccessTokenByRefreshToken(userTokenUpdateRTO.getRefreshToken()))
					.refreshToken(userTokenUpdateRTO.getRefreshToken())
					.build(), HttpStatus.OK);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "Token Update");
		}
	}

	public ResponseEntity<UserRTO> deleteUser(@PathVariable("id") String id) {
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
}