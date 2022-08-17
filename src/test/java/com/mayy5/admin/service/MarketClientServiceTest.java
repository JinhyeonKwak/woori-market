package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.Address;
import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.type.RetailType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MarketClientServiceTest {

    @Autowired
    private MarketService marketService;
    @Autowired
    private MarketClientService marketClientService;


    @BeforeEach
    @Transactional
    void init() {
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
                        .retailType(RetailType.JOGBAL)
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
                    .marketDay(DayOfWeek.of(value))
                    .build();

            marketService.createMarket("admin", marketAgent, retailerList, market);

        });
    }

    @Test
    @Transactional
    void getRetailersAroundClient() {

        Market market = marketService.getMarket(1L);
        List<Retailer> retailerList = market.getRetailerList();

        Double lat = Double.valueOf(market.getAddress().getLatitude());
        Double lng = Double.valueOf(market.getAddress().getLongitude());
        List<Retailer> retailersAroundClient = marketClientService.getRetailersAroundClient(lat, lng);

        assertThat(market.getRetailerList().size()).isEqualTo(20);
        assertThat(retailersAroundClient.size()).isEqualTo(20);
        IntStream.range(0, 20).forEach(i ->
                assertThat(retailerList.get(i).getId()).isEqualTo(retailersAroundClient.get(i).getId()));



    }
}