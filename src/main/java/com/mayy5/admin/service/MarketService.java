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
import javax.persistence.TypedQuery;
import java.time.DayOfWeek;
import java.time.LocalDateTime;
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

    /**
     * 장날이 되는 경우 출석 체크 필드 초기화
     * @param marketId
     * @param retailerId
     */
    @Transactional
    public void updateAttend(Long marketId, Long retailerId) {
        // 장 조회
        Market market = marketRepository.findOne(marketId);
        Retailer retailer = retailerRepository.findOne(retailerId);

        // MarketRetailer 관계 엔티티 조회
        List<MarketRetailer> marketRetailers = em.createQuery("select r from MarketRetailer r " +
                        "where r.market = :market ", MarketRetailer.class)
                .setParameter("market", market)
                .getResultList();

        // 장날이 되는 경우 모든 retailer의 출석 체크를 출석 처리
        DayOfWeek marketDay = market.getMarketDay();
        DayOfWeek today = LocalDateTime.now().getDayOfWeek();
        if (marketDay == today) {
            for (MarketRetailer marketRetailer : marketRetailers) {
                em.createQuery("update MarketRetailer r set r.checkAttend = :attend where r = :marketRetailer")
                        .setParameter("attend", CheckAttend.PRESENT)
                        .setParameter("marketRetailer", marketRetailer); // 테스트 필요
            }

        }
    }

    /**
     * 메서드 호출시 결석 처리
     * @param marketId
     * @param retailerId
     */
    @Transactional
    public void checkAttend(Long marketId, Long retailerId) {
        // 장 조회
        Market market = marketRepository.findOne(marketId);
        Retailer retailer = retailerRepository.findOne(retailerId);

        // MarketRetailer 관계 엔티티 조회
        MarketRetailer marketRetailer = em.createQuery("select r from MarketRetailer r where r.market = :market " +
                        "and r.retailer = :retailer", MarketRetailer.class)
                .setParameter("market", market)
                .setParameter("retailer", retailer)
                .getSingleResult(); // 조회 결과가 없는 경우 예외 처리 필요

        // 결석 처리
        marketRetailer.setCheckAttend(CheckAttend.ABSENT);
    }


}
