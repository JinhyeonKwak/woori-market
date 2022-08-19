package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.Retailer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketClientService {

    private final MarketService marketService;
    private final MarketMapService marketMapService;

    @Transactional(readOnly = true)
    public List<Retailer> getRetailersAroundClient(Double lat, Double lng) {

        String regionCode = marketMapService.getRegionCodeAround(lat, lng);
        List<Market> markets = marketService.getMarketsByRegionCode(regionCode);
        List<Retailer> retailerList = markets.stream()
                .map(market -> marketService.getRetailersOfMarket(market.getId()))
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
        return retailerList;
    }

    @Transactional
    public void makeReservation() {
        
    }

    @Transactional
    public void getMarket() {

    }


}
