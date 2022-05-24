package com.mayy5.admin;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.repository.MarketRepository;
import com.mayy5.admin.repository.PostRepository;
import com.mayy5.admin.service.MarketAgentService;
import com.mayy5.admin.service.MarketService;
import com.mayy5.admin.service.RetailerService;
import com.mayy5.admin.type.*;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.service.UserService;

@EnableScheduling
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Order(value = 1)
	@Bean
	public CommandLineRunner adminUser(UserService userService, PasswordEncoder passwordEncoder, PostRepository postRepository) {
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

			IntStream.rangeClosed(1,200).forEach(index -> {
				postRepository.save(Post.builder()
					.title("게시글" + index)
					.subTitle("순서" + index)
					.content("콘텐츠")
					.postType(PostType.FREE)
					.createAt(LocalDateTime.now())
					.updateAt(LocalDateTime.now())
					.user(user)
					.build());
			});

		};
	}

	@Order(value = 2)
	@Bean
	public ApplicationRunner applicationRunner(UserService userService,
											   MarketService marketService,
											   MarketRepository marketRepository,
											   MarketAgentService marketAgentService,
											   RetailerService retailerService
											   ) {
		return args -> {
		};
	}



}
