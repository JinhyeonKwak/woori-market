package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketService {

	private final MarketAgentService marketAgentService;
	private final RetailerService retailerService;
//	private final MarketScheduleService marketScheduleService;
	private final EntityManager em;

	private final MarketRepository marketRepository;

	@Transactional
	public Market createMarket(String loginUserId,
							   MarketAgent inputMarketAgent,
							   List<Retailer> inputRetailerList,
							   Market inputMarket) throws IOException, ParseException {

		MarketAgent marketAgent = marketAgentService.createMarketAgent(loginUserId, inputMarketAgent);
		List<Retailer> retailerList = inputRetailerList.stream()
			.map(retailer -> retailerService.createRetailer(retailer))
			.collect(Collectors.toList());

		String regionCode = MarketMapService.getRegionCode(inputMarket.getRoadAddress());
		Map<String, String> latLng = MarketMapService.getLatLng(inputMarket.getRoadAddress());
		inputMarket.setRegionCode(regionCode);
		inputMarket.setLatitude(latLng.get("latitude"));
		inputMarket.setLongitude(latLng.get("longitude"));

		Market market = marketRepository.save(Market.createMarket(marketAgent, inputMarket, retailerList));

//		marketScheduleService.createSchedule(marketRetailers);

		return market;
	}

	@Transactional(readOnly = true)
	public Market getMarket(Long marketId) {
		return marketRepository.findById(marketId)
			.orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Market"));
	}

	@Transactional
	public Market addRetailers(Long marketId, List<Retailer> retailerList) {
		Market market = this.getMarket(marketId);
		retailerList.stream()
				.map(retailerService::createRetailer)
				.forEach(retailer -> market.getRetailerList().add(retailer));
		return market;
	}

	@Transactional
	public Market dropRetailers(Long marketId, List<Long> retailerIds) {
		Market market = this.getMarket(marketId);
		for (Long retailerId : retailerIds) {
			retailerService.deleteRetailer(retailerId);
		}
		return market;
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

//	@Transactional
//	public MarketSchedule checkAttend(Long marketId, Long retailerId, LocalDate checkDate) {
//		return marketScheduleService.checkAttend(marketId, retailerId, checkDate);
//	}

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

}
