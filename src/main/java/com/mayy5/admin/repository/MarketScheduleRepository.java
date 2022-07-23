package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.MarketSchedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MarketScheduleRepository extends JpaRepository<MarketSchedule, Long> {

    @Query("select s from MarketSchedule s where s.market.id = :marketId")
    List<MarketSchedule> getSchedulesOfMarket(Long marketId);

    @Query("select s from MarketSchedule s where s.market.id = :marketId " +
            "and s.retailer.id = :retailerId and s.marketDate = :checkDate")
    MarketSchedule getSchedule(Long marketId, Long retailerId, LocalDate checkDate);
}
