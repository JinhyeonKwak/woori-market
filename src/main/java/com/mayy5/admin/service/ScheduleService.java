package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.repository.MarketRetailerRepository;
import com.mayy5.admin.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MarketRetailerRepository marketRetailerRepository;


    @Transactional
    public Schedule createSchedule(Long marketId, Long retailerId) {
        MarketRetailer marketRetailer = marketRetailerRepository.getMarketRetailer(marketId, retailerId);
        Schedule schedule = Schedule.createSchedule(marketRetailer);
        return scheduleRepository.save(schedule);
    }

    @Transactional(readOnly = true)
    public Schedule getSchedule(Long marketId, Long retailerId) {
        MarketRetailer marketRetailer = marketRetailerRepository.getMarketRetailer(marketId, retailerId);
        Schedule schedule = scheduleRepository.getSchedule(marketRetailer, LocalDate.now());

        return schedule;
    }

    // 해당 Market의 모든 스케줄 조회
    @Transactional(readOnly = true)
    public List<Schedule> getScheduleOfMarket(Long marketId) {
        return scheduleRepository.getScheduleOfMarket(marketId);
    }

    @Transactional
    public Schedule checkAttend(Long marketId, Long retailerId) {
        Schedule schedule = this.getSchedule(marketId, retailerId);
        schedule.setCheckAttend(false); // 결석
        return scheduleRepository.save(schedule);
    }
}
