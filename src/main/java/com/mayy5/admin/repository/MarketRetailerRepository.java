package com.mayy5.admin.repository;

import java.time.DayOfWeek;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mayy5.admin.model.domain.MarketRetailer;

@Repository
public interface MarketRetailerRepository extends JpaRepository<MarketRetailer, Long> {

	@Query("select r from MarketRetailer r where r.market.id = :marketId and r.retailer.id = :retailerId")
	MarketRetailer getMarketRetailer(Long marketId, Long retailerId);

	@Query("select x from MarketRetailer x where x.retailer.id = :retailerId")
	List<MarketRetailer> getMarketRetailersByRetailerId(Long retailerId);

	@Query("select x from MarketRetailer x where x.market.marketDay = :today")
	List<MarketRetailer> getMarketRetailersOfToday(DayOfWeek today);
}
