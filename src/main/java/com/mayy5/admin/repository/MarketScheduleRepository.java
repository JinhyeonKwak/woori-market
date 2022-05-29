package com.mayy5.admin.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.mayy5.admin.model.domain.MarketRetailer;
import com.mayy5.admin.model.domain.MarketSchedule;

@Repository
public interface MarketScheduleRepository extends JpaRepository<MarketSchedule, Long> {

	@Query("select s from MarketSchedule s where s.marketRetailer = :marketRetailer and s.marketDate = :currentDate")
	MarketSchedule getSchedule(MarketRetailer marketRetailer, LocalDate currentDate);

	@Query("select s from MarketSchedule  s where s.marketRetailer.market.id = :marketId")
	List<MarketSchedule> getScheduleOfMarket(Long marketId);
}
