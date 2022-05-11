package com.mayy5.admin.controller;

import com.mayy5.admin.apis.MarketAgentApi;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.mapper.MarketAgentMapper;
import com.mayy5.admin.model.mapper.UserMapper;
import com.mayy5.admin.model.req.MarketAgentRequest;
import com.mayy5.admin.model.res.MarketAgentResponse;
import com.mayy5.admin.model.res.UserRTO;
import com.mayy5.admin.service.MarketAgentService;
import com.mayy5.admin.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarketAgentController implements MarketAgentApi {

    private final MarketAgentService marketAgentService;
    private final UserService userService;

    private final MarketAgentMapper marketAgentMapper;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<MarketAgentResponse> createMarketAgent(@RequestBody @Valid MarketAgentRequest marketAgentRequest) {
        MarketAgent input = marketAgentMapper.toEntity(marketAgentRequest);
        MarketAgent marketAgent = marketAgentService.createMarketAgent(input);
        return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketAgentResponse> getMarketAgent(@RequestBody @Valid String userId) {
        User user = userService.getUser(userId);
        Long marketAgentId = user.getMarketAgent().getId();
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketAgentResponse> updateMarketAgent(@RequestBody @Valid MarketAgentRequest marketAgentRequest) {
        String userId = marketAgentRequest.getUser().getId();
        Long marketAgentId = userService.getUser(userId).getMarketAgent().getId();

        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        marketAgentMapper.update(marketAgentRequest, marketAgent);
        MarketAgent updateMarketAgent = marketAgentService.updateMarketAgent(marketAgent);

        return new ResponseEntity<>(marketAgentMapper.toDto(updateMarketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRTO> deleteMarketAgent(@RequestBody @Valid String userId) {
        User user = userService.getUser(userId);
        Long marketAgentId = user.getMarketAgent().getId();
        marketAgentService.deleteMarketAgent(marketAgentId);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }
}
