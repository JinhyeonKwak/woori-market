package com.mayy5.admin.controller;

import com.mayy5.admin.apis.MarketApi;
import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.domain.MarketSchedule;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.mapper.MarketAgentMapper;
import com.mayy5.admin.model.mapper.MarketMapper;
import com.mayy5.admin.model.mapper.RetailerMapper;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.req.MarketUpdateRequestDto;
import com.mayy5.admin.model.req.RetailerRequestDto;
import com.mayy5.admin.model.res.MarketAgentResponseDto;
import com.mayy5.admin.model.res.MarketResponseDto;
import com.mayy5.admin.model.res.ScheduleResponseDto;
import com.mayy5.admin.service.MarketService;
import com.mayy5.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarketController implements MarketApi {

    private final MarketService marketService;
    private final UserService userService;


    private final MarketMapper marketMapper;
    private final MarketAgentMapper marketAgentMapper;
    private final RetailerMapper retailerMapper;

    @Override
    public ResponseEntity<MarketResponseDto> createMarket(MarketCreateRequestDto marketCreateRequestDto) {

        try {
            String loginUserId = userService.getLoginUserId();

            MarketAgent inputMarketAgent = marketAgentMapper.toEntity(marketCreateRequestDto);
            List<RetailerRequestDto> retailerRequestList = marketCreateRequestDto.getRetailers();
            List<Retailer> retailerList = retailerMapper.toEntities(retailerRequestList);
            Market inputMarket = marketMapper.toMarket(marketCreateRequestDto);
            Market market = marketService.createMarket(loginUserId, inputMarketAgent, retailerList, inputMarket);

            return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "createMarket");
        }
    }

    @Override
    public ResponseEntity<MarketResponseDto> getMarket(Long marketId) {

        try {
            Market market = marketService.getMarket(marketId);
            return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "getMarket");
        }
    }

    @Override
    public ResponseEntity<MarketResponseDto> updateMarket(Long marketId, MarketUpdateRequestDto marketRequest) {

        try {
            Market findMarket = marketService.getMarket(marketId);
            Market market = marketMapper.patchMarket(marketRequest, findMarket);
            Market updateMarket = marketService.updateMarket(market);
            return new ResponseEntity<>(marketMapper.toMarketResponse(updateMarket), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "updateMarket");
        }
    }

    @Override
    public ResponseEntity<MarketAgentResponseDto> deleteMarket(Long marketId) {
        try {
            Market findMarket = marketService.getMarket(marketId);
            MarketAgent marketAgent = findMarket.getMarketAgent();
            marketService.deleteMarket(marketId);
            return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "deleteMarket");
        }
    }

    @Override
    public ResponseEntity<List<ScheduleResponseDto>> checkAttend(Long marketId, List<Long> retailerIds, LocalDate checkDate) {
        try {
            List<MarketSchedule> marketScheduleList = marketService.checkAttend(marketId, retailerIds, checkDate);
            return new ResponseEntity<>(marketMapper.toScheduleResponseList(marketScheduleList), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "checkAttend");
        }
    }

    //==장주 관련==//
    @Override
    public ResponseEntity<MarketResponseDto> registerMarketAgent(Long marketId, Long marketAgentId) {
        try {
            Market market = marketService.registerMarketAgent(marketId, marketAgentId);
            return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "registerMarketAgent");
        }
    }

    @Override
    public ResponseEntity<List<MarketResponseDto>> getMarketsOfMarketAgent(Long marketAgentId) {

        try {
            List<Market> marketList = marketService.getMarketsOfMarketAgent(marketAgentId);
            List<MarketResponseDto> marketResponseDtoList = marketMapper.toMarketResponseList(marketList);
            return new ResponseEntity<>(marketResponseDtoList, HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "getMarketsOfMarketAgent");
        }
    }

    //==장원 관련==//
    @Override
    public ResponseEntity<MarketResponseDto> registerRetailers(Long marketId, List<RetailerRequestDto> retailerRequestDtos) {

        try {
            List<Retailer> retailerList = retailerMapper.toEntities(retailerRequestDtos);
            Market market = marketService.addRetailers(marketId, retailerList);
            return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "registerRetailers");
        }
    }

    @Override
    public ResponseEntity<MarketResponseDto> dropRetailers(Long marketId, List<Long> retailerIds) {

        try {
            Market market = marketService.dropRetailers(marketId, retailerIds);
            return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "dropRetailer");
        }
    }
}
