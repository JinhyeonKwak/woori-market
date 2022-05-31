package com.mayy5.admin;

import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.model.mapper.MarketMapper;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.repository.PostRepository;
import com.mayy5.admin.security.AuthConstant;
import com.mayy5.admin.service.MarketService;
import com.mayy5.admin.service.UserService;
import com.mayy5.admin.type.PostType;
import com.mayy5.admin.type.UserMetaType;
import com.mayy5.admin.type.UserRoleType;
import org.json.simple.parser.ParseException;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.core.annotation.Order;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.IntStream;

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
	public CommandLineRunner mockUpMarket(MarketService marketService, MarketMapper marketMapper) {
		return args -> {

			String[] addresses = {"서울특별시 종로구 혜화동 20-12", "서울특별시 성동구 성수동1가 685-700", "서울특별시 용산구 원효로3가 124-1",
			"서울특별시 동대문구 답십리동 1003", "서울특별시 성북구 삼선동1가 214-1", "경기도 고양시 덕양구 주교동 600", "경기도 수원시 권선구 금곡동 1081",
			"대전광역시 동구 중동 94-10", "부산광역시 중구 부평동4가 57-1", "울산광역시 중구 태화동 412-2"};

			IntStream.rangeClosed(1, 10).forEach(i -> {
				MarketAgent marketAgent = MarketAgent.builder()
						.agentName("NAME" + i)
						.corporateName("CORP" + i)
						.build();

				List<Retailer> retailerList = new ArrayList<>();
				IntStream.rangeClosed(1, 20).forEach(j -> {
					Retailer retailer = Retailer.builder()
							.retailerName("NAME" + j)
							.retailType("TYPE" + j)
							.build();
					retailerList.add(retailer);
				});

				double random = Math.random();
				int value = (int) (random * 7 + 1);
				MarketCreateRequestDto marketRequest = MarketCreateRequestDto.builder()
						.locationAddress(addresses[i-1])
						.detailAddress("DETAIL ADDRESS" + i)
						.startDate(LocalDate.now().plusWeeks(value))
						.endDate(LocalDate.now().plusWeeks(value).plusYears(1))
						.marketDay(DayOfWeek.of(value))
						.build();

				Market market = null;
				try {
					market = marketMapper.toEntity(marketRequest);
				} catch (IOException e) {
					e.printStackTrace();
				} catch (ParseException e) {
					e.printStackTrace();
				}

				marketService.createMarket("admin", marketAgent, retailerList, market);
			});
		};
	}



}
