package com.mayy5.admin.controller;

import com.mayy5.admin.apis.MarketApi;
import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.model.dto.MarketDTO;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.mapper.MarketAgentMapper;
import com.mayy5.admin.model.mapper.MarketMapper;
import com.mayy5.admin.model.req.MarketRequest;
import com.mayy5.admin.model.res.MarketAgentResponse;
import com.mayy5.admin.model.res.MarketResponse;
import com.mayy5.admin.model.res.ScheduleResponse;
import com.mayy5.admin.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarketController implements MarketApi {

    private final MarketService marketService;
    private final UserService userService;
    private final MarketAgentService marketAgentService;
    private final RetailerService retailerService;
    private final ScheduleService scheduleService;

    private final MarketMapper marketMapper;
    private final MarketAgentMapper marketAgentMapper;

    @Override
    public ResponseEntity<MarketResponse> createMarket(@RequestBody @Valid MarketRequest marketRequest) {
        Long marketAgentId = marketRequest.getMarketAgentId();
        MarketDTO marketDTO = marketMapper.toMarketDTO(marketRequest);
        Market market = marketService.createMarket(marketAgentId, marketDTO);
        return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
    }

    @Override
    public List<ResponseEntity<MarketResponse>> getMarketsOfMarketAgent(@PathVariable String userId) {
        User user = userService.getUser(userId);
        MarketAgent findMarketAgent = user.getMarketAgent();
        MarketAgent marketAgent = marketAgentService.getMarketAgent(findMarketAgent.getId());
        List<Market> marketList = marketAgent.getMarketList();
        List<ResponseEntity<MarketResponse>> responseEntities = marketList.stream()
                .map(market -> new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK))
                .collect(Collectors.toList());
        return responseEntities;
    }

    @Override
    public List<ResponseEntity<MarketResponse>> getMarketsOfRetailer(@PathVariable Long retailerId) {
        Retailer findRetailer = retailerService.getRetailer(retailerId);
        List<MarketRetailer> marketRetailerList = findRetailer.getMarketRetailerList();
        List<ResponseEntity<MarketResponse>> responseEntities = marketRetailerList.stream()
                .map(MarketRetailer::getMarket)
                .map(market -> new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK))
                .collect(Collectors.toList());
        return responseEntities;
    }

    @Override
    public ResponseEntity<MarketResponse> getMarket(@PathVariable Long marketId) {
        Market market = marketService.getMarket(marketId);
        return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketResponse> updateMarket(@PathVariable Long marketId,
                                                       @RequestBody @Valid MarketRequest marketRequest) {
        Market market = marketService.getMarket(marketId);
        marketMapper.update(marketRequest, market);
        Market updateMarket = marketService.updateMarket(market);
        return new ResponseEntity<>(marketMapper.toMarketResponse(updateMarket), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketAgentResponse> deleteMarket(@PathVariable Long marketId) {
        Market findMarket = marketService.getMarket(marketId);
        MarketAgent marketAgent = findMarket.getMarketAgent();
        marketService.deleteMarket(marketId);
        return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketResponse> approveRetailer(@PathVariable Long retailerId, @PathVariable Long marketId) {
        marketService.approveRetailer(marketId, retailerId);
        Market market = marketService.getMarket(marketId);
        return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ScheduleResponse> checkAttend(@PathVariable Long retailerId, @PathVariable Long marketId) {
        Schedule schedule = scheduleService.checkAttend(marketId, retailerId);
        return new ResponseEntity<>(marketMapper.toScheduleResponse(schedule), HttpStatus.OK);
    }


}
