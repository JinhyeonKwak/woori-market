package com.mayy5.admin;

import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.repository.PostRepository;
import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.service.MarketService;
import com.mayy5.admin.service.UserService;
import com.mayy5.admin.type.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

@EnableJpaAuditing
@SpringBootApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpringApplication.run(AdminApplication.class, args);
	}

	@Profile({"dev","local","real", "test"})
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
					.user(user)
					.build());
			});

		};
	}


	@Profile({"dev", "local", "test"})
	@Order(value = 2)
	@Bean
	public CommandLineRunner mockUpMarket(MarketService marketService) {
		return args -> {

			String[] jibunAddresses = {"서울특별시 종로구 혜화동 20-12", "서울특별시 성동구 성수동1가 685-700", "서울특별시 용산구 원효로3가 124-1",
			"서울특별시 동대문구 답십리동 1003", "서울특별시 성북구 삼선동1가 214-1", "경기도 고양시 덕양구 주교동 600", "경기도 수원시 권선구 금곡동 1081",
			"대전광역시 동구 중동 94-10", "부산광역시 중구 부평동4가 57-1", "울산광역시 중구 태화동 412-2"};
			String[] roadAddresses = {"서울특별시 종로구 혜화로8길 25", "서울특별시 성동구 왕십리로 83-21", "서울특별시 용산구 원효로 129",
			"서울특별시 동대문구 답십리로 130", "서울특별시 성북구 삼선교로6길 32-9", "경기도 고양시 덕양구 고양시청로 10", "경기도 수원시 권선구 금곡로 46",
			"대전광역시 동구 중앙로200번길 3", "부산광역시 중구 보수대로64번길 11", "울산광역시 중구 신기12길 38"};


			IntStream.rangeClosed(1, 10).forEach(i -> {
				MarketAgent marketAgent = MarketAgent.builder()
						.agentName("NAME" + i)
						.corporateName("CORP" + i)
						.build();

				List<Retailer> retailerList = new ArrayList<>();
				IntStream.rangeClosed(1, 20).forEach(j -> {
					Retailer retailer = Retailer.builder()
							.retailerName("NAME" + j)
							.retailType(RetailType.SNACK)
							.startAt("9")
							.endAt("17")
							.retailSubtypeList(new ArrayList<>(Arrays.asList(RetailSubtype.TTEOKBOKKI, RetailSubtype.JOKBAL)))
							.build();
					retailerList.add(retailer);
				});

				double random = Math.random();
				int value = (int) (random * 7 + 1);
				Address address = Address.builder()
						.roadAddress(roadAddresses[i - 1])
						.jibunAddress(jibunAddresses[i - 1])
						.build();
				Market market = Market.builder()
						.address(address)
						.startDate(LocalDate.now().plusWeeks(value))
						.endDate(LocalDate.now().plusWeeks(value).plusYears(1))
						.openAt("9")
						.closedAt("20")
						.marketDay(DayOfWeek.of(value))
						.build();

				marketService.createMarket("admin", marketAgent, retailerList, market);
			});
		};
	}


}
