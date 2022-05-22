package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.MarketRetailer;
import com.mayy5.admin.model.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where s.marketRetailer = :marketRetailer and s.marketDate = :currentDate")
    Schedule getSchedule(MarketRetailer marketRetailer, LocalDate currentDate);

    @Query("select s from Schedule  s where s.marketRetailer.market.id = :marketId")
    List<Schedule> getScheduleOfMarket(Long marketId);
}
