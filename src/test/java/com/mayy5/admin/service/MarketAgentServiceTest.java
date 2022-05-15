package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.model.mapper.MarketAgentMapper;
import com.mayy5.admin.model.req.MarketAgentRequest;
import com.mayy5.admin.type.MarketAgentMetaType;
import com.mayy5.admin.type.UserMetaType;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.Map;

@SpringBootTest
class MarketAgentServiceTest {

    @Autowired
    MarketAgentService marketAgentService;
    @Autowired
    UserService userService;

    @Autowired
    MarketAgentMapper marketAgentMapper;

    @Test
    @Transactional
    @Rollback(value = false)
    void createMarketAgent() {
        // 유저 생성
        User userInput = new User();
        userInput.setId("jinhyeon");
        userInput.setPassword("1234");
        Map<UserMetaType, String> inputMeta = new HashMap<>();
        inputMeta.put(UserMetaType.ROLE, "ROLE_MARKET_AGENT");
        userInput.setMeta(inputMeta);
        User user = userService.createUser(userInput);

        // marketAgentRequest 생성
        MarketAgentRequest marketAgentRequest = new MarketAgentRequest();
        marketAgentRequest.setUser(user);

        // marketAgent 영속성 엔티티 생성
        MarketAgent input = marketAgentMapper.toEntity(marketAgentRequest);
        MarketAgent marketAgent = marketAgentService.createMarketAgent(input);

        // marketAgent 조회
        MarketAgent ma = marketAgentService.getMarketAgent(marketAgent.getId());
        Assertions.assertThat(ma).isEqualTo(marketAgent);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void updateMarketAgent() {
        // 유저 생성
        User userInput = new User();
        userInput.setId("jinhyeon");
        userInput.setPassword("1234");
        Map<UserMetaType, String> inputMeta = new HashMap<>();
        inputMeta.put(UserMetaType.ROLE, "ROLE_MARKET_AGENT");
        userInput.setMeta(inputMeta);
        User user = userService.createUser(userInput);

        // marketAgentRequest
        MarketAgentRequest marketAgentRequest = new MarketAgentRequest();
        marketAgentRequest.setUser(user);
        Map<MarketAgentMetaType, String> meta = new HashMap<>();
        meta.put(MarketAgentMetaType.CORPORATE_NAME, "NATURE");
        marketAgentRequest.setMeta(meta);

        // marketAgent 영속성 엔티티 생성
        MarketAgent input = marketAgentMapper.toEntity(marketAgentRequest);
        MarketAgent marketAgent = marketAgentService.createMarketAgent(input);

        // marketAgentRequest2
        String userId = marketAgentRequest.getUser().getId();
        Long marketAgentId = userService.getUser(userId).getMarketAgent().getId();
        MarketAgent ma = marketAgentService.getMarketAgent(marketAgentId);

        Map<MarketAgentMetaType, String> updateMeta = new HashMap<>();
        updateMeta.put(MarketAgentMetaType.CORPORATE_NAME, "SCIENCE");
        MarketAgentRequest marketAgentRequest2 = new MarketAgentRequest(user, updateMeta);
        marketAgentMapper.update(marketAgentRequest2, ma); // 여기서 변경 감지로 인해 이미 수정이 됨

        // marketAgent 수정
        marketAgentService.updateMarketAgent(marketAgent);
    }

    @Test
    @Transactional
    @Rollback(value = false)
    void deleteMarketAgent() {
        // 유저 생성
        User userInput = new User();
        userInput.setId("jinhyeon");
        userInput.setPassword("1234");
        Map<UserMetaType, String> inputMeta = new HashMap<>();
        inputMeta.put(UserMetaType.ROLE, "ROLE_MARKET_AGENT");
        userInput.setMeta(inputMeta);
        User user = userService.createUser(userInput);

        // marketAgentRequest
        MarketAgentRequest marketAgentRequest = new MarketAgentRequest();
        marketAgentRequest.setUser(user);
        Map<MarketAgentMetaType, String> meta = new HashMap<>();
        meta.put(MarketAgentMetaType.CORPORATE_NAME, "NATURE");
        marketAgentRequest.setMeta(meta);


        // marketAgent 영속성 엔티티 생성
        MarketAgent input = marketAgentMapper.toEntity(marketAgentRequest);
        MarketAgent marketAgent = marketAgentService.createMarketAgent(input);

        // 유저 조회 & 장주 조회
        User target = userService.getUser(user.getId());
        Long marketAgentId = target.getMarketAgent().getId();

        // 장주 삭제
        marketAgentService.deleteMarketAgent(marketAgentId);

    }
}

