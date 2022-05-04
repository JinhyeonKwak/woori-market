package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.repository.MarketAgentRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;


@SpringBootTest
@Transactional
class MarketAgentServiceTest {

    @Autowired
    MarketAgentService marketAgentService;

    @Autowired
    MarketAgentRepository marketAgentRepository;

    @Test
    public void 장주등록() throws Exception {
        MarketAgent ma = new MarketAgent();
        ma.setUserName("kwak");

        Long savedId = marketAgentService.join(ma);

        Assertions.assertEquals(ma, marketAgentRepository.findOne(savedId));
    }

    @Test
    public void 장주_중복_예외() throws Exception {
        MarketAgent ma1 = new MarketAgent();
        ma1.setUserName("kwak");

        MarketAgent ma2 = new MarketAgent();
        ma2.setUserName("kwak");

        marketAgentService.join(ma1);
        try {
            marketAgentService.join(ma2);
        } catch (IllegalStateException e) {
            return;
        }

        Assertions.fail("예외가 발생해야 한다.");
    }

}