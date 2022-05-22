package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.MarketAgent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketAgentRepository extends JpaRepository<MarketAgent, Long> {

    @Query("select m from MarketAgent m where m.user.id = :userId")
    List<MarketAgent> getMarketAgentsByUserId(String userId);
}
