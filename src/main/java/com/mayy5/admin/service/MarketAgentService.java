package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.repository.MarketAgentRepository;
import com.mayy5.admin.type.MarketAgentMetaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketAgentService {

    private final MarketAgentRepository marketAgentRepository;
    private final UserService userService;

    @Transactional
    public MarketAgent createMarketAgent(String userId, MarketAgent input) {
        User user = userService.getUser(userId);
        Map<MarketAgentMetaType, String> meta = input.getMeta();
        MarketAgent marketAgent = MarketAgent.createMarketAgent(user, meta);
        return marketAgentRepository.save(marketAgent);
    }

    @Transactional(readOnly = true)
    public MarketAgent getMarketAgent(Long marketAgentId) {
        return marketAgentRepository.findById(marketAgentId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "MarketAgent"));
    }

    @Transactional
    public MarketAgent updateMarketAgent(MarketAgent marketAgent) throws CommonException {
        marketAgentRepository.findById(marketAgent.getId())
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "MarketAgent"));
        return marketAgentRepository.save(marketAgent);
    }

    @Transactional(readOnly = true)
    public List<MarketAgent> getMarketAgentsByUserId(String userId) {
        return marketAgentRepository.getMarketAgentsByUserId(userId);
    }
}
