package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.Market;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarketRepository extends JpaRepository<Market, Long> {

    @Query("select m from Market m where m.address.regionCode = :regionCode")
    List<Market> getMarketsByRegionCode(String regionCode);
}
