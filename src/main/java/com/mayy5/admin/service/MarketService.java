package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.model.dto.MarketDTO;
import com.mayy5.admin.repository.MarketAgentRepository;
import com.mayy5.admin.repository.MarketRepository;
import com.mayy5.admin.repository.RetailerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketRepository marketRepository;
    private final MarketAgentRepository marketAgentRepository;
    private final RetailerRepository retailerRepository;
    private final EntityManager em;

    /**
     * 장 등록
     * @param marketAgentId
     * @param marketDTO
     * @return marketId
     */
    @Transactional
    public Long createMarket(Long marketAgentId, MarketDTO marketDTO) {

        // 장 조회
        MarketAgent marketAgent = marketAgentRepository.findOne(marketAgentId);

        // 장 생성
        Market market = Market.createMarket(marketAgent, marketDTO);
        marketRepository.save(market);

        return market.getId();
    }

    /**
     * 장원 승인 메서드
     * @param marketId
     * @param retailerIdList
     */
    @Transactional
    public void approveRetailers(Long marketId, List<Long> retailerIdList) {
        // 장 조회
        Market market = marketRepository.findOne(marketId);

        // 장원 조회
        ArrayList<Retailer> retailers = new ArrayList<>();
        for (Long retailerId : retailerIdList) {
            Retailer retailer = retailerRepository.findOne(retailerId);
            retailers.add(retailer);
        }

        // 장원 승인
        market.addRetailer(retailers);
    }




}
