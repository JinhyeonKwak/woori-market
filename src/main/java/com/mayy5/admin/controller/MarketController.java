package com.mayy5.admin.controller;

import com.mayy5.admin.apis.MarketApi;
import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.model.dto.MarketDTO;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.mapper.MarketAgentMapper;
import com.mayy5.admin.model.mapper.MarketMapper;
import com.mayy5.admin.model.mapper.RetailerMapper;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.req.MarketUpdateRequestDto;
import com.mayy5.admin.model.req.RetailerRequestDto;
import com.mayy5.admin.model.res.MarketAgentResponseDto;
import com.mayy5.admin.model.res.MarketResponseDto;
import com.mayy5.admin.model.res.RetailerResponseDto;
import com.mayy5.admin.model.res.ScheduleResponseDto;
import com.mayy5.admin.service.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

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
    private final RetailerMapper retailerMapper;

    @Override
    public ResponseEntity<MarketResponseDto> createMarket(MarketCreateRequestDto marketCreateRequestDto) {

        // Market 생성
        MarketDTO marketDTO = marketMapper.marketCreateDTOtoMarketDTO(marketCreateRequestDto);
        Market market = marketService.createMarket(marketDTO);
        Long marketId = market.getId();

        // MarketAgent 생성 & 등록
        MarketAgent inputMarketAgent = marketAgentMapper.marketCreateDTOtoEntity(marketCreateRequestDto);
        String loginUserId = userService.getLoginUserId();
        User user = userService.getUser(loginUserId);
        inputMarketAgent.setUser(user);
        MarketAgent marketAgent = marketAgentService.createMarketAgent(inputMarketAgent);
        market.setMarketAgent(marketAgent);

        // Retailer 일괄 생성 & 등록
        List<RetailerRequestDto> retailerRequestList = marketCreateRequestDto.getRetailerRequestDtoList();

        retailerRequestList.stream()
                .map(retailerMapper::toEntity)
                .forEach(inputRetailer -> {
            inputRetailer.setUser(user);
            Retailer retailer = retailerService.createRetailer(inputRetailer);
            marketService.addRetailer(marketId, retailer.getId());
        });

        marketService.updateMarket(market);
        return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketResponseDto> getMarket(Long marketId) {
        Market market = marketService.getMarket(marketId);
        return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketResponseDto> updateMarket(Long marketId, MarketUpdateRequestDto marketRequest) {
        Market market = marketService.getMarket(marketId);
        marketMapper.update(marketRequest, market);
        Market updateMarket = marketService.updateMarket(market);
        return new ResponseEntity<>(marketMapper.toMarketResponse(updateMarket), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketAgentResponseDto> deleteMarket(Long marketId) {
        Market findMarket = marketService.getMarket(marketId);
        MarketAgent marketAgent = findMarket.getMarketAgent();
        marketService.deleteMarket(marketId);
        return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<ScheduleResponseDto> checkAttend(Long marketId, Long retailerId) {
        Schedule schedule = scheduleService.checkAttend(marketId, retailerId);
        return new ResponseEntity<>(marketMapper.toScheduleResponse(schedule), HttpStatus.OK);
    }

    //==장주 관련==//
    @Override
    public ResponseEntity<MarketAgentResponseDto> registerMarketAgent(Long marketId, Long marketAgentId) {
        Market market = marketService.getMarket(marketId);
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        market.setMarketAgent(marketAgent);
        marketService.updateMarket(market);
        return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
    }

    @Override
    public List<ResponseEntity<MarketResponseDto>> getMarketsOfMarketAgent(Long marketAgentId) {
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        List<Market> marketList = marketAgent.getMarketList();
        List<ResponseEntity<MarketResponseDto>> responseEntities = marketList.stream()
                .map(market -> new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK))
                .collect(Collectors.toList());
        return responseEntities;
    }

    //==장원 관련==//
    @Override
    public ResponseEntity<RetailerResponseDto> registerRetailer(Long marketId, Long retailerId) {
        Market market = marketService.getMarket(marketId);
        Retailer retailer = retailerService.getRetailer(retailerId);
        marketService.addRetailer(marketId, retailerId);
        marketService.updateMarket(market);
        return new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK);
    }

    @Override
    public List<ResponseEntity<MarketResponseDto>> getMarketsOfRetailer(Long retailerId) {
        List<MarketRetailer> marketRetailerList = retailerService.getMarketRetailersOfRetailer(retailerId);
        List<ResponseEntity<MarketResponseDto>> responseEntities = marketRetailerList.stream()
                .map(MarketRetailer::getMarket)
                .map(market -> new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK))
                .collect(Collectors.toList());
        return responseEntities;
    }

    @Override
    public ResponseEntity<MarketResponseDto> dropRetailer(Long marketId, Long retailerId) {
        marketService.dropRetailer(marketId, retailerId);
        Market market = marketService.getMarket(marketId);
        return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
    }
}
