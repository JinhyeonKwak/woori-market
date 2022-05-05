package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.repository.MarketAgentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MarketAgentService {

    private final MarketAgentRepository marketAgentRepository;

    @Transactional
    public Long join(MarketAgent marketAgent) {
        marketAgentRepository.save(marketAgent);
        return marketAgent.getId();
    }

    @Transactional(readOnly = true)
    public List<MarketAgent> findAgents() {
        return marketAgentRepository.findAll();
    }

    @Transactional(readOnly = true)
    public MarketAgent findOne(Long marketAgentId) {
        return marketAgentRepository.findOne(marketAgentId);
    }

}
