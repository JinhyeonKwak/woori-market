package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.MarketRetailer;
import com.mayy5.admin.model.domain.MarketSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MarketScheduleRepository extends JpaRepository<MarketSchedule, Long> {

    @Query("select s from MarketSchedule s where s.marketRetailer = :marketRetailer and s.marketDate = :currentDate")
    MarketSchedule getSchedule(MarketRetailer marketRetailer, LocalDate currentDate);

    @Query("select s from MarketSchedule  s where s.marketRetailer.market.id = :marketId")
    List<MarketSchedule> getScheduleOfMarket(Long marketId);
}
