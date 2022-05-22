package com.mayy5.admin.controller;

import com.mayy5.admin.apis.MarketMemberApi;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.domain.MarketRetailer;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.mapper.MarketAgentMapper;
import com.mayy5.admin.model.mapper.RetailerMapper;
import com.mayy5.admin.model.mapper.UserMapper;
import com.mayy5.admin.model.req.MarketAgentRequestDto;
import com.mayy5.admin.model.req.RetailerRequestDto;
import com.mayy5.admin.model.res.MarketAgentResponseDto;
import com.mayy5.admin.model.res.RetailerResponseDto;
import com.mayy5.admin.model.res.UserRTO;
import com.mayy5.admin.repository.MarketRetailerRepository;
import com.mayy5.admin.service.MarketAgentService;
import com.mayy5.admin.service.RetailerService;
import com.mayy5.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarketMemberController implements MarketMemberApi {

    private final MarketAgentService marketAgentService;
    private final RetailerService retailerService;
    private final UserService userService;

    private final RetailerMapper retailerMapper;
    private final UserMapper userMapper;
    private final MarketAgentMapper marketAgentMapper;

    @Override
    public ResponseEntity<MarketAgentResponseDto> createMarketAgent(MarketAgentRequestDto marketAgentRequestDto) {
        MarketAgent input = marketAgentMapper.toEntity(marketAgentRequestDto);
        String loginUserId = userService.getLoginUserId();
        User user = userService.getUser(loginUserId);
        input.setUser(user);
        MarketAgent marketAgent = marketAgentService.createMarketAgent(input);
        return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
    }

    @Override
    public List<ResponseEntity<MarketAgentResponseDto>> getMarketAgents() {
        String loginUserId = userService.getLoginUserId();
        List<MarketAgent> marketAgentList = marketAgentService.getMarketAgentsByUserId(loginUserId);
        List<ResponseEntity<MarketAgentResponseDto>> responseEntities = marketAgentList.stream()
                .map(marketAgent -> new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK))
                .collect(Collectors.toList());
        return responseEntities;
    }

    @Override
    public ResponseEntity<MarketAgentResponseDto> getMarketAgent(Long marketAgentId) {
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketAgentResponseDto> updateMarketAgent(MarketAgentRequestDto marketAgentRequestDto,
                                                                    Long marketAgentId) {
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        marketAgentMapper.update(marketAgentRequestDto, marketAgent);
        MarketAgent updateMarketAgent = marketAgentService.updateMarketAgent(marketAgent);

        return new ResponseEntity<>(marketAgentMapper.toDto(updateMarketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRTO> deleteMarketAgent(Long marketAgentId) {
        String loginUserId = userService.getLoginUserId();
        User user = userService.getUser(loginUserId);
        marketAgentService.deleteMarketAgent(marketAgentId);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }



    @Override
    public ResponseEntity<RetailerResponseDto> createRetailer(RetailerRequestDto retailerRequest) {
        Retailer input = retailerMapper.toEntity(retailerRequest);
        String loginUserId = userService.getLoginUserId();
        User user = userService.getUser(loginUserId);
        input.setUser(user);
        Retailer retailer = retailerService.createRetailer(input);
        return new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK);
    }

    @Override
    public List<ResponseEntity<RetailerResponseDto>> getRetailers() {
        String loginUserId = userService.getLoginUserId();
        List<Retailer> retailerList = retailerService.getRetailersByUserId(loginUserId);
        List<ResponseEntity<RetailerResponseDto>> responseEntities = retailerList.stream()
                .map(retailer -> new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK))
                .collect(Collectors.toList());
        return responseEntities;
    }

    @Override
    public ResponseEntity<RetailerResponseDto> getRetailer(Long retailerId) {
        Retailer retailer = retailerService.getRetailer(retailerId);
        return new ResponseEntity<>(retailerMapper.toDto(retailer), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<RetailerResponseDto> updateRetailer(Long retailerId, RetailerRequestDto retailerRequest) {
        Retailer retailer = retailerService.getRetailer(retailerId);
        retailerMapper.update(retailerRequest, retailer);
        Retailer updateRetailer = retailerService.updateRetailer(retailer);
        return new ResponseEntity<>(retailerMapper.toDto(updateRetailer), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRTO> deleteRetailer(Long retailerId) {
        User user = retailerService.getRetailer(retailerId).getUser();
        retailerService.deleteRetailer(retailerId);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }
}