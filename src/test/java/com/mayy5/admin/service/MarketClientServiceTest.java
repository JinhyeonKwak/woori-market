package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.Retailer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

@Transactional
@SpringBootTest
class MarketClientServiceTest {

    @Autowired
    private MarketService marketService;
    @Autowired
    private MarketClientService marketClientService;

    @Test
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