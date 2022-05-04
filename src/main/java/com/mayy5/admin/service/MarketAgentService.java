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

        validateDuplicateAgent(marketAgent); //중복 장주 검증
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


    //==기타 메서드==//
    private void validateDuplicateAgent(MarketAgent marketAgent) {
        List<MarketAgent> findAgents = marketAgentRepository.findByName(marketAgent.getUserName());
        if (!findAgents.isEmpty()) {
            throw new IllegalStateException("이미 등록되었습니다.");
        }
    }
}
