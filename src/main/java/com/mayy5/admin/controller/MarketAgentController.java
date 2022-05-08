package com.mayy5.admin.controller;

import com.mayy5.admin.apis.MarketAgentApi;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.mapper.MarketAgentMapper;
import com.mayy5.admin.model.mapper.UserMapper;
import com.mayy5.admin.model.req.MarketAgentCreateRTO;
import com.mayy5.admin.model.req.MarketAgentUpdateRTO;
import com.mayy5.admin.model.res.MarketAgentRTO;
import com.mayy5.admin.model.res.UserRTO;
import com.mayy5.admin.service.MarketAgentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.List;


@Slf4j
@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = "*")
public class MarketAgentController implements MarketAgentApi {

    private final MarketAgentService marketAgentService;

    private final MarketAgentMapper marketAgentMapper;
    private final UserMapper userMapper;

    @Override
    public ResponseEntity<MarketAgentRTO> createMarketAgent(@RequestBody @Valid MarketAgentCreateRTO marketAgentCreateRTO) {

        MarketAgent input = marketAgentMapper.toEntity(marketAgentCreateRTO);
        MarketAgent marketAgent = marketAgentService.createMarketAgent(input);
        return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<List<MarketAgentRTO>> getMarketAgentList(@RequestBody @Valid String userId) {
        List<MarketAgent> marketAgentList = marketAgentService.getMarketAgentList(userId);
        List<MarketAgentRTO> marketAgentRTOs = new ArrayList<>();
        for (MarketAgent marketAgent : marketAgentList) {
            marketAgentRTOs.add(marketAgentMapper.toDto(marketAgent));
        }
        return new ResponseEntity<>(marketAgentRTOs, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketAgentRTO> getMarketAgent(@PathVariable Long marketAgentId) {
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        return new ResponseEntity<>(marketAgentMapper.toDto(marketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<MarketAgentRTO> updateMarketAgent(@PathVariable Long marketAgentId,
                                                            @RequestBody @Valid MarketAgentUpdateRTO marketAgentUpdateRTO) {
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        marketAgentMapper.write(marketAgentUpdateRTO, marketAgent);
        MarketAgent updateMarketAgent = marketAgentService.updateMarketAgent(marketAgent);
        return new ResponseEntity<>(marketAgentMapper.toDto(updateMarketAgent), HttpStatus.OK);
    }

    @Override
    public ResponseEntity<UserRTO> deleteMarketAgent(@PathVariable Long marketAgentId) {
        MarketAgent marketAgent = marketAgentService.getMarketAgent(marketAgentId);
        User user = marketAgent.getUser();
        marketAgentService.deleteMarketAgent(marketAgentId);
        return new ResponseEntity<>(userMapper.toDto(user), HttpStatus.OK);
    }
}
