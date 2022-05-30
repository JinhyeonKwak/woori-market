package com.mayy5.admin;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mayy5.admin.model.domain.Address;
import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.domain.Post;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.repository.PostRepository;
import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.service.MarketService;
import com.mayy5.admin.service.UserService;
import com.mayy5.admin.type.MarketAgentMetaType;
import com.mayy5.admin.type.PostType;
import com.mayy5.admin.type.RetailerMetaType;
import com.mayy5.admin.type.UserMetaType;
import com.mayy5.admin.type.UserRoleType;

@EnableScheduling
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Profile({"dev", "local","prod"})
	@Order(value = 1)
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

	@Profile({"dev", "local"})
	@Order(value = 2)
	@Bean
	public CommandLineRunner mockUpMarket(UserService userService, PostRepository postRepository) {
		return args -> {

			User user = userService.getUser(AuthConstant.ADMIN_USER);
			IntStream.rangeClosed(1, 200).forEach(index -> {
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

	@Profile({"dev", "local"})
	@Order(value = 3)
	@Bean
	public CommandLineRunner mockUpMarket(MarketService marketService) {
		return args -> {

			IntStream.rangeClosed(1, 10).forEach(i -> {
				MarketAgent marketAgent = new MarketAgent();
				marketAgent.getMeta().put(MarketAgentMetaType.CORPORATE_NAME, "CORP" + i);

				List<Retailer> retailerList = new ArrayList<>();
				IntStream.rangeClosed(1, 20).forEach(j -> {
					Retailer retailer = new Retailer();
					retailer.getMeta().put(RetailerMetaType.BUSINESS_TYPE, "BUSINESS" + j);
					retailerList.add(retailer);
				});

				double random = Math.random();
				int value = (int)(random * 7 + 1);
				Market market = Market.builder()
					.address(new Address("street" + i, "detail" + i, "code" + i))
					.startDate(LocalDate.now().plusWeeks(value))
					.endDate(LocalDate.now().plusWeeks(value).plusYears(1))
					.marketDay(DayOfWeek.of(value))
					.build();
				marketService.createMarket(AuthConstant.ADMIN_USER, marketAgent, retailerList, market);
			});
		};
	}

}
