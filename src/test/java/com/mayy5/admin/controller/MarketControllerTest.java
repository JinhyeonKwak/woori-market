package com.mayy5.admin.controller;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.req.MarketAgentRequestDto;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.req.RetailerRequestDto;
import com.mayy5.admin.model.res.MarketResponseDto;
import com.mayy5.admin.repository.MarketRepository;
import com.mayy5.admin.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MarketControllerTest {

    @LocalServerPort
    private int port;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    @Autowired
    private MarketRepository marketRepository;


    @Test
    @Transactional
    void createMarket() {

        // given
        MarketAgentRequestDto marketAgentRequest = MarketAgentRequestDto.builder()
                .agentName("KWAK")
                .corporateName("NATURE")
                .build();

        List<RetailerRequestDto> retailerRequestDtoList = IntStream.range(0, 10)
                .mapToObj(i -> RetailerRequestDto.builder()
                        .retailerName("NAME" + i)
                        .retailType("SEAFOOD")
                        .build()).collect(Collectors.toList());

        MarketCreateRequestDto marketCreateRequestDto = MarketCreateRequestDto.builder()
                .roadAddress("서울특별시 종로구 혜화로8길 25")
                .jibunAddress("서울특별시 종로구 혜화동 20-12")
                .startDate(LocalDate.of(2022, 8, 6))
                .endDate(LocalDate.of(2023, 8, 6))
                .marketDay(DayOfWeek.FRIDAY)
                .marketAgent(marketAgentRequest)
                .retailers(retailerRequestDtoList)
                .build();

        // when
        HttpHeaders headers = new HttpHeaders();
        headers.set("Authorization", "debug");

        HttpEntity<MarketCreateRequestDto> request = new HttpEntity<>(marketCreateRequestDto, headers);
        ResponseEntity<MarketResponseDto> response = restTemplate.exchange(
                "http://localhost:" + port + "/v1/markets",
                HttpMethod.POST,
                request,
                MarketResponseDto.class
        );

        // then
        assertEquals(response.getStatusCode(), HttpStatus.OK);
        List<Market> all = marketRepository.findAll();
        Market findMarket = all.get(0);
        assertEquals(findMarket.getAddress().getRoadAddress(), "서울특별시 종로구 혜화로8길 25");
        assertEquals(findMarket.getAddress().getJibunAddress(), "서울특별시 종로구 혜화동 20-12");
        assertEquals(findMarket.getStartDate(), LocalDate.of(2022, 8, 6));
        assertEquals(findMarket.getEndDate(), LocalDate.of(2023, 8, 6));
        assertEquals(findMarket.getMarketDay(), DayOfWeek.FRIDAY);
        assertEquals(findMarket.getMarketAgent().getAgentName(), "KWAK");
        assertEquals(findMarket.getMarketAgent().getCorporateName(), "NATURE");
        assertEquals(findMarket.getRetailerList().stream().count(), 10);

    }

    @Test
    void getMarket() {


    }

    @Test
    void updateMarket() {
    }

    @Test
    void deleteMarket() {
    }

    @Test
    void checkAttend() {
    }

    @Test
    void registerMarketAgent() {
    }

    @Test
    void getMarketsOfMarketAgent() {
    }

    @Test
    void registerRetailers() {
    }

    @Test
    void dropRetailers() {
    }
}