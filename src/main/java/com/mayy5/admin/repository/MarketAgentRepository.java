package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.MarketAgent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketAgentRepository extends JpaRepository<MarketAgent, Long> {

    @Query("select m from MarketAgent m where m.user.id = :userId")
    MarketAgent getMarketAgentByUserId(String userId);
}
