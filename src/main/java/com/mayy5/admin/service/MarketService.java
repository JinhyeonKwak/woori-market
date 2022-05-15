package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.model.dto.MarketDTO;
import com.mayy5.admin.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;

    private final MarketAgentService marketAgentService;
    private final RetailerService retailerService;
    private final ScheduleService scheduleService;


    @Transactional
    public Market createMarket(Long marketAgentId, MarketDTO marketDTO) {

        // 장주 조회
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);

        // 장 생성
        Market market = Market.createMarket(marketAgent, marketDTO);
        return marketRepository.save(market);
    }

    @Transactional(readOnly = true)
    public Market getMarket(Long marketId) {
        return marketRepository.findById(marketId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Market"));
    }

    @Transactional
    public void approveRetailer(Long marketId, Long retailerId) {
        // 장 조회
        Market market = this.getMarket(marketId);

        // 장원 조회
        Retailer retailer = retailerService.getRetailer(retailerId);

        // 장원 승인
        market.addRetailer(retailer);

        // 출석 관리 엔티티 생성
        scheduleService.createSchedule(marketId, retailerId);
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
}
