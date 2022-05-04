package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MarketRepository {

    private final EntityManager em;

    public void save(Market market) {
        em.persist(market);
    }

    public Market findOne(Long id) {
        return em.find(Market.class, id);
    }

    // 필터에 따른 검색 기능 추가 예정
//    public List<Market> findAll(OrderSearch orderSearch) {
//
//    }

    public List<Market> findAll() {
        return em.createQuery("select m from Market m", Market.class)
                .getResultList();
    }


}
