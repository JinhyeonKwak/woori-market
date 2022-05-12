package com.mayy5.admin.repository;

import com.mayy5.admin.model.domain.MarketRetailer;
import com.mayy5.admin.model.domain.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Long> {

    @Query("select s from Schedule s where s.marketRetailer = :marketRetailer and s.createAt = :currentDate")
    Schedule getSchedule(MarketRetailer marketRetailer, LocalDate currentDate);

}
