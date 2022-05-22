package com.mayy5.admin;

import java.util.HashMap;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.service.UserService;
import com.mayy5.admin.type.UserMetaType;
import com.mayy5.admin.type.UserRoleType;

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
			User adminUser = userService.getUser(AuthConstant.ADMIN_USER);

			Address address = new Address("street1", "room1", "123");

			Market market = marketRepository.save(Market.builder()
												.address(address)
												.startDate(LocalDate.now())
												.endDate(LocalDate.now())
												.marketDay(DayOfWeek.FRIDAY)
												.marketRetailerList(new ArrayList<>())
												.build());

			Map<MarketAgentMetaType, String> marketAgentMeta = new HashMap<>();
			marketAgentMeta.put(MarketAgentMetaType.CORPORATE_NAME, "NATURE");
			MarketAgent marketAgent = marketAgentService.createMarketAgent(MarketAgent.createMarketAgent(adminUser, marketAgentMeta));
			market.setMarketAgent(marketAgent);

			Map<RetailerMetaType, String> retailerMeta = new HashMap<>();
			retailerMeta.put(RetailerMetaType.BUSINESS_TYPE, "HOT_DOG");
			Retailer retailer = retailerService.createRetailer(Retailer.createRetailer(adminUser, retailerMeta));
			marketService.addRetailer(market.getId(), retailer.getId());

			marketService.updateMarket(market);
		};
	}



}
