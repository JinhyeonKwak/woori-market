package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.MarketAgent;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.List;

@Repository
@RequiredArgsConstructor
public class MarketAgentRepository {

    private final EntityManager em;

    public void save(MarketAgent marketAgent) {
        em.persist(marketAgent);
    }

    public MarketAgent findOne(Long id) {
        return em.find(MarketAgent.class, id);
    }

    public List<MarketAgent> findAll() {
        return em.createQuery("select m from MarketAgent m", MarketAgent.class)
                .getResultList();
    }

}
