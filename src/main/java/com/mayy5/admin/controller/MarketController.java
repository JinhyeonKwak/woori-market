package com.mayy5.admin.controller;

import com.mayy5.admin.apis.MarketApi;
import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.model.mapper.MarketAgentMapper;
import com.mayy5.admin.model.mapper.MarketMapper;
import com.mayy5.admin.model.mapper.RetailerMapper;
import com.mayy5.admin.model.req.CheckAttendDTO;
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


    private final MarketMapper marketMapper;
    private final MarketAgentMapper marketAgentMapper;
    private final RetailerMapper retailerMapper;

    @Override
    public ResponseEntity<MarketResponseDto> createMarket(MarketCreateRequestDto marketCreateRequestDto) {

        try {
            String loginUserId = userService.getLoginUserId();

            MarketAgent inputMarketAgent = marketAgentMapper.toEntity(marketCreateRequestDto);
            List<RetailerRequestDto> retailerRequestList = marketCreateRequestDto.getRetailerRequestDtoList();
            List<Retailer> retailerList = retailerMapper.toEntities(retailerRequestList);
            Market inputMarket = marketMapper.toEntity(marketCreateRequestDto);

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
            Market market = marketService.getMarket(marketId);
            marketMapper.update(marketRequest, market);
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
    public ResponseEntity<ScheduleResponseDto> checkAttend(CheckAttendDTO checkAttendDTO) {
        try {
            MarketSchedule marketSchedule = marketService.checkAttend(checkAttendDTO.getMarketId(),
                                                                    checkAttendDTO.getRetailerId(),
                                                                    checkAttendDTO.getCheckDate());
            return new ResponseEntity<>(marketMapper.toScheduleResponse(marketSchedule), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "checkAttend");
        }


    }

    //==장주 관련==//
    @Override
    public ResponseEntity<MarketAgentResponseDto> registerMarketAgent(Long marketId, Long marketAgentId) {
        try {
            MarketAgent marketAgent = marketService.registerMarketAgent(marketId, marketAgentId);
            return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
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
            List<MarketResponseDto> marketResponseDtoList = marketList.stream()
                    .map(marketMapper::toMarketResponse)
                    .collect(Collectors.toList());
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
    public ResponseEntity<RetailerResponseDto> registerRetailer(Long marketId, Long retailerId) {

        try {
            Retailer retailer = marketService.registerRetailer(marketId, retailerId);
            return new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "registerRetailer");
        }
    }

    @Override
    public ResponseEntity<MarketResponseDto> dropRetailer(Long marketId, Long retailerId) {

        try {
            marketService.dropRetailer(marketId, retailerId);
            Market market = marketService.getMarket(marketId);
            return new ResponseEntity<>(marketMapper.toMarketResponse(market), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "dropRetailer");
        }
    }

    @Override
    public ResponseEntity<List<MarketResponseDto>> getMarketsOfRetailer(Long retailerId) {

        try {
            List<MarketRetailer> marketRetailerList = marketService.getMarketsOfRetailer(retailerId);
            return new ResponseEntity<>(marketRetailerList.stream()
                    .map(marketRetailer -> marketMapper.toMarketResponse(marketRetailer.getMarket()))
                    .collect(Collectors.toList()), HttpStatus.OK);
        } catch (CommonException e) {
            throw e;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "getMarketsOfRetailer");
        }
    }
}
