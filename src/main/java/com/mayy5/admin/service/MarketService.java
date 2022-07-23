package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.repository.MarketRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.io.IOException;
import java.time.LocalDate;
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
	private final MarketScheduleService marketScheduleService;
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

		String roadAddress = inputMarket.getAddress().getRoadAddress();
		String jibunAddress = inputMarket.getAddress().getJibunAddress();
		String regionCode = MarketMapService.getRegionCode(roadAddress);
		Map<String, String> latLng = MarketMapService.getLatLng(roadAddress);
		Address address = Address.builder()
				.roadAddress(roadAddress)
				.jibunAddress(jibunAddress)
				.regionCode(regionCode)
				.latitude(latLng.get("latitude"))
				.longitude(latLng.get("longitude"))
				.build();

		Market market = marketRepository.save(Market.createMarket(inputMarket, address, marketAgent, retailerList));

		marketScheduleService.createSchedule(market, retailerList);

		return market;
	}

	@Transactional(readOnly = true)
	public Market getMarket(Long marketId) {
		return marketRepository.findById(marketId)
			.orElseThrow(() -> new CommonException(BError.NOT_EXIST, "Market"));
	}

	@Transactional
	public Market addRetailers(Long marketId, List<Retailer> retailerList) {
		Market market = getMarket(marketId);
		List<Retailer> retailers = retailerList.stream()
				.map(retailerService::createRetailer)
				.collect(Collectors.toList());
		market.addRetailerList(retailers);
		return market;
	}

	@Transactional
	public Market dropRetailers(Long marketId, List<Long> retailerIds) {
		Market market = getMarket(marketId);
		retailerIds.forEach(retailerService::deleteRetailer);
		return market;
	}

	@Transactional
	public Market updateMarket(Market market) {
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
	public List<MarketSchedule> checkAttend(Long marketId, List<Long> retailerIds, LocalDate checkDate) {
		List<MarketSchedule> marketScheduleList = retailerIds.stream()
				.map(retailerId -> marketScheduleService.checkAttend(marketId, retailerId, checkDate))
				.collect(Collectors.toList());
		return marketScheduleList;
	}

	@Transactional
	public Market registerMarketAgent(Long marketId, Long marketAgentId) {
		Market market = getMarket(marketId);
		MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
		market.setMarketAgent(marketAgent);
		return market;
	}

	@Transactional(readOnly = true)
	public List<Market> getMarketsOfMarketAgent(Long marketAgentId) {
		MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
		return marketAgent.getMarketList();
	}

}
