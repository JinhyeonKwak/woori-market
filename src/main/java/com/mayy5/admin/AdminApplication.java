package com.mayy5.admin;

import java.util.HashMap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.service.UserService;
import com.mayy5.admin.type.UserMetaType;
import com.mayy5.admin.type.UserRoleType;

@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Bean
	public CommandLineRunner adminUser(UserService userService, PasswordEncoder passwordEncoder) {
		return args -> {
			User user = User.builder()
				.id(AuthConstant.ADMIN_USER)
				.password(passwordEncoder.encode(AuthConstant.ADMIN_PWD))
				.email("mayy5.master@gmail.com")
				.name("master")
				.phone("010-0000-0000")
				.meta(new HashMap<>())
				.build();
			user.getMeta().put(UserMetaType.ROLE, UserRoleType.ROLE_ADMIN.name());
			userService.createUser(user);
		};
	}
}
