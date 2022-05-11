package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketRetailer;
import com.mayy5.admin.model.domain.Retailer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface MarketRetailerRepository extends JpaRepository<MarketRetailer, Long> {

    @Query("select r from MarketRetailer r where r.market = :market and r.retailer = :retailer")
    MarketRetailer getMarketRetailer(Market market, Retailer retailer);

}
