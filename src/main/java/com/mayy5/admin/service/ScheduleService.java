package com.mayy5.admin.service;

import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.repository.MarketRetailerRepository;
import com.mayy5.admin.repository.ScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final MarketRetailerRepository marketRetailerRepository;

//    @Scheduled(initialDelay = 60000, fixedDelay = 1000 * 3600 * 24)
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 해당 요일에 열리는 장 스케줄 생성
    @Transactional
    public void createSchedule() {
        List<MarketRetailer> marketRetailers = marketRetailerRepository.getMarketRetailersOfToday(DayOfWeek.from(LocalDate.now()));
        marketRetailers.stream()
                .map(Schedule::createSchedule)
                .forEach(scheduleRepository::save);
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
        MarketRetailer marketRetailer = marketRetailerRepository.getMarketRetailer(marketId, retailerId);
        Schedule schedule = scheduleRepository.getSchedule(marketRetailer, LocalDate.now());
        schedule.setCheckAttend(true); // 출석
        return scheduleRepository.save(schedule);
    }
}
