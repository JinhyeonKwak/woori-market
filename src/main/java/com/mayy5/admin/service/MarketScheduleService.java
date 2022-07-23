package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketSchedule;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.repository.MarketScheduleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MarketScheduleService {

    private final MarketScheduleRepository marketScheduleRepository;

    @Transactional
    public void createSchedule(Market market, List<Retailer> retailerList) {
        LocalDate startDate = market.getStartDate();
        LocalDate endDate = market.getEndDate();
        int diff = market.getMarketDay().getValue() - DayOfWeek.from(startDate).getValue();
        if (diff < 0) diff += 7;
        LocalDate start = startDate.plusDays(diff);

        List<LocalDate> dateList = new ArrayList<>();
        dateList.add(start);
        int daysToAdd = 7;
        for (int i = 0; dateList.get(i).isBefore(endDate); i++) {
            dateList.add(LocalDate.from(start.plusDays(daysToAdd)));
            LocalDate date = dateList.get(i);
            retailerList.stream()
                    .map(retailer -> MarketSchedule.createSchedule(market, retailer, date))
                    .forEach(marketScheduleRepository::save);
            daysToAdd += 7;
        }
    }

    // 해당 Market의 모든 스케줄 조회
    @Transactional(readOnly = true)
    public List<MarketSchedule> getScheduleOfMarket(Long marketId) {
        return marketScheduleRepository.getSchedulesOfMarket(marketId);
    }

    // 출석 체크
    @Transactional
    public MarketSchedule checkAttend(Long marketId, Long retailerId, LocalDate checkDate) {

        MarketSchedule marketSchedule = marketScheduleRepository.getSchedule(marketId, retailerId, checkDate);
        if (marketSchedule == null) {
            throw new CommonException(BError.NOT_EXIST, "MarketSchedule");
        }
        marketSchedule.setCheckAttend(marketSchedule.getCheckAttend() == false);
        return marketScheduleRepository.save(marketSchedule);
    }
}
