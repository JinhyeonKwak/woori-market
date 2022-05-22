package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketRetailer;
import com.mayy5.admin.model.domain.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.util.List;

@Repository
public interface MarketRetailerRepository extends JpaRepository<MarketRetailer, Long> {

    @Query("select r from MarketRetailer r where r.market.id = :marketId and r.retailer.id = :retailerId")
    MarketRetailer getMarketRetailer(Long marketId, Long retailerId);

    @Query("select x from MarketRetailer x where x.retailer.id = :retailerId")
    List<MarketRetailer> getMarketRetailersByRetailerId(Long retailerId);

    @Query("select x from MarketRetailer x where x.market.marketDay = :today")
    List<MarketRetailer> getMarketRetailersOfToday(DayOfWeek today);
}
