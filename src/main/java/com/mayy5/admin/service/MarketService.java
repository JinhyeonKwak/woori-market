package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.repository.MarketRepository;
import com.mayy5.admin.repository.MarketRetailerRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketService {

    private final MarketAgentService marketAgentService;
    private final RetailerService retailerService;
    private final MarketScheduleService marketScheduleService;
    private final EntityManager em;

    private final MarketRepository marketRepository;
    private final MarketRetailerRepository marketRetailerRepository;



    @Transactional
    public Market createMarket(String loginUserId,
                               MarketAgent inputMarketAgent,
                               List<Retailer> inputRetailerList,
                               Market input) {

        MarketAgent marketAgent = marketAgentService.createMarketAgent(loginUserId, inputMarketAgent);
        List<Retailer> retailerList = inputRetailerList.stream()
                .map(retailer -> retailerService.createRetailer(loginUserId, retailer))
                .collect(Collectors.toList());
        Market market = marketRepository.save(Market.createMarket(marketAgent, input));

        List<MarketRetailer> marketRetailers = this.addRetailers(market, retailerList);
        this.updateMarket(market);
        marketScheduleService.createSchedule(marketRetailers);

        return market;
    }

    @Transactional(readOnly = true)
    public Market getMarket(Long marketId) {
        return marketRepository.findById(marketId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Market"));
    }

    @Transactional
    public void addRetailer(Market market, Retailer retailer) {;

        // 장-장원 관계 엔티티 생성 & 영속화
        MarketRetailer marketRetailer = MarketRetailer.createMarketRetailer(market, retailer);
        em.persist(marketRetailer);

    }

    @Transactional
    public List<MarketRetailer> addRetailers(Market market, List<Retailer> retailerList) {
        List<MarketRetailer> marketRetailers = new ArrayList<>();
        retailerList.stream()
                .map(retailer -> MarketRetailer.createMarketRetailer(market, retailer))
                .forEach(marketRetailer -> {
            em.persist(marketRetailer);
            marketRetailers.add(marketRetailer);
        });
        return marketRetailers;
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

    @Transactional
    public MarketSchedule checkAttend(Long marketId, Long retailerId, LocalDate checkDate) {
        return marketScheduleService.checkAttend(marketId, retailerId, checkDate);
    }

    @Transactional
    public MarketAgent registerMarketAgent(Long marketId, Long marketAgentId) {
        Market market = this.getMarket(marketId);
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        market.setMarketAgent(marketAgent);
        this.updateMarket(market);
        return marketAgent;
    }

    @Transactional(readOnly = true)
    public List<Market> getMarketsOfMarketAgent(Long marketAgentId) {
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        return marketAgent.getMarketList();
    }

    @Transactional
    public Retailer registerRetailer(Long marketId, Long retailerId) {
        Market market = this.getMarket(marketId);
        Retailer retailer = retailerService.getRetailer(retailerId);
        this.addRetailer(market, retailer);
        this.updateMarket(market);
        return retailer;
    }

    @Transactional(readOnly = true)
    public List<MarketRetailer> getMarketsOfRetailer(Long retailerId) {
        return retailerService.getMarketRetailersOfRetailer(retailerId);
    }
}
