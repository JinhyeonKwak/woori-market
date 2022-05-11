package com.mayy5.admin.service;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import com.mayy5.admin.model.domain.MarketAgent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.repository.UserRepository;
import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.type.UserMetaType;
import com.mayy5.admin.type.UserRoleType;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@RequiredArgsConstructor
public class UserService {

	final static Set<UserRoleType> USER_ROLE_FILTER_SET = new HashSet<>(Arrays.asList(
		UserRoleType.ROLE_MARKET_AGENT,
		UserRoleType.ROLE_RETAILER));

	private final UserRepository userRepository;

	@Transactional
	public User getUser(String id) {
		User user = userRepository.findById(id)
			.orElseThrow(() -> new CommonException(BError.NOT_EXIST, "User"));
		return user;
	}

	@Transactional
	public List<User> getUserList() {
		List<User> userList = userRepository.findAll();
		return userList;
	}

	@Transactional
	public User createUser(User input) {
		try {
			userRepository.findById(input.getId()).ifPresent(user -> {
				throw new CommonException(BError.EXIST, "ID");
			});
			return userRepository.save(setUser(input));
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(e.getMessage(), e);
			throw new CommonException(BError.FAIL_REASON, "User Create", e.getMessage());
		}
	}

	@Transactional
	public User updateUser(User input) throws CommonException {
		userRepository.findById(input.getId())
			.orElseThrow(() -> new CommonException(BError.NOT_EXIST, "User"));
		return userRepository.save(setUser(input));
	}

	@Transactional
	public void deleteUser(String id) throws CommonException {
		try {

			Optional<User> user = userRepository.findById(id);
			user.ifPresent(resourceExist -> {
				userRepository.deleteById(id);
			});
			return;
		} catch (Exception e) {
			log.error(e.getMessage());
			log.debug(e.getMessage(), e);
			throw new CommonException(BError.FAIL, "User Delete");
		}
	}

	@Transactional
	protected User setUser(User input) {

		// Admin User를 제외한 나머지는 Role에 대한 검사를 진행한다.
		if (!input.getId().equals(AuthConstant.ADMIN_USER) &&
			!USER_ROLE_FILTER_SET.containsAll(
				Arrays.stream(input.getMeta().get(UserMetaType.ROLE).split(","))
					.map(s -> UserRoleType.valueOf(s))
					.collect(Collectors.toSet()))) {
			throw new CommonException(BError.NOT_MATCH, "User Role");
		}

		User user = userRepository.findById(input.getId()).orElse(
			User.builder()
				.id(input.getId())
				.meta(input.getMeta())
				.build()
		);

		// Modify Only Input Data Exist like Patch Method
		if (Objects.nonNull(input.getPassword()))
			user.setPassword(input.getPassword());
		if (Objects.nonNull(input.getMeta())) {
			user.setMeta(input.getMeta());
			if (Objects.nonNull(user.getMeta().get(UserMetaType.MAIL))) {
				userRepository.findAllExceptOne(user.getId()).stream()
					.filter(u -> Objects.nonNull(u.getMeta().get(UserMetaType.MAIL)))
					.filter(r -> r.getMeta().get(UserMetaType.MAIL)
						.equals(user.getMeta().get(UserMetaType.MAIL))
					).findFirst().ifPresent(r -> {
					throw new CommonException(BError.EXIST, "User Mail");
				});
			}
		}

		// # Default JDBC Setting
		return user;
	}

}
