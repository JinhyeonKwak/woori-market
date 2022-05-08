package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.repository.MarketAgentRepository;
import com.mayy5.admin.type.MarketAgentMetaType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketAgentService {

    private final MarketAgentRepository marketAgentRepository;

    private final UserService userService;

    @Transactional
    public MarketAgent createMarketAgent(MarketAgent input) {
        User user = input.getUser();
        Map<MarketAgentMetaType, String> meta = input.getMeta();
        return MarketAgent.createMarketAgent(user, meta);
    }

    @Transactional(readOnly = true)
    public MarketAgent getMarketAgent(Long marketAgentId) {
        return marketAgentRepository.findById(marketAgentId)
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "MarketAgent"));
    }

    @Transactional(readOnly = true)
    public List<MarketAgent> getMarketAgentList(String userId) {
        User user = userService.getUser(userId);
        return marketAgentRepository.getMarketAgentList(user);
    }

    @Transactional
    public MarketAgent updateMarketAgent(MarketAgent marketAgent) throws CommonException {
        marketAgentRepository.findById(marketAgent.getId())
                .orElseThrow(() -> new CommonException(BError.NOT_EXIST, "MarketAgent"));
        return marketAgentRepository.save(marketAgent);
    }

    @Transactional
    public void deleteMarketAgent(Long marketAgentId) throws CommonException {
        try {
            Optional<MarketAgent> marketAgent = marketAgentRepository.findById(marketAgentId);
            marketAgent.ifPresent(resourceExist -> {
                marketAgentRepository.deleteById(marketAgentId);
            });
            return;
        } catch (Exception e) {
            log.error(e.getMessage());
            log.debug(e.getMessage(), e);
            throw new CommonException(BError.FAIL, "MarketAgent Delete");
        }
    }

}
