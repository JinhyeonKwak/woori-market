package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.model.dto.MarketDTO;
import com.mayy5.admin.repository.MarketRepository;
import com.mayy5.admin.repository.MarketRetailerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketRetailerRepository marketRetailerRepository;

    private final RetailerService retailerService;
    private final EntityManager em;


    @Transactional
    public Market createMarket(Market input ) {

        // 장 생성
        Market market = Market.createMarket(input);
        return marketRepository.save(market);
    }

    @Transactional(readOnly = true)
    public Market getMarket(Long marketId) {
        return marketRepository.findById(marketId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Market"));
    }

    @Transactional
    public void addRetailer(Long marketId, Long retailerId) {
        // 장 조회
        Market market = this.getMarket(marketId);

        // 장원 조회
        Retailer retailer = retailerService.getRetailer(retailerId);

        // 장-장원 관계 엔티티 생성 & 영속화
        MarketRetailer marketRetailer = MarketRetailer.createMarketRetailer(market, retailer);
        em.persist(marketRetailer);

        // 연관 관계
        market.getMarketRetailerList().add(marketRetailer);
        retailer.getMarketRetailerList().add(marketRetailer);

    }

    @Transactional
    public Market updateMarket(Market market) {
        marketRepository.findById(market.getId())
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "market"));
        return marketRepository.save(market);
    }

    @Transactional
    public void deleteMarket(Long marketId) throws CommonException {
        try {
            Optional<Market> market = marketRepository.findById(marketId);
            market.ifPresent(resourceExist -> {
                marketRepository.deleteById(marketId);
            });
            return;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "Market Delete");
        }
    }

    @Transactional
    public void dropRetailer(Long marketId, Long retailerId) {
        MarketRetailer marketRetailer = marketRetailerRepository.getMarketRetailer(marketId, retailerId);
        marketRetailerRepository.deleteById(marketRetailer.getId());
    }
}
