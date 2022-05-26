package com.mayy5.admin.service;

import com.mayy5.admin.common.BError;
import com.mayy5.admin.common.CommonException;
import com.mayy5.admin.model.domain.*;
import com.mayy5.admin.repository.MarketRetailerRepository;
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
    private final MarketRetailerRepository marketRetailerRepository;

    @Transactional
    public void createSchedule(List<MarketRetailer> marketRetailers) {
        Market market = marketRetailers.get(0).getMarket();
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
            marketRetailers.stream()
                    .map(marketRetailer -> MarketSchedule.createSchedule(marketRetailer, date))
                    .forEach(marketScheduleRepository::save);
            daysToAdd += 7;
        }
    }

    @Transactional(readOnly = true)
    public MarketSchedule getSchedule(Long marketId, Long retailerId) {
        MarketRetailer marketRetailer = marketRetailerRepository.getMarketRetailer(marketId, retailerId);
        MarketSchedule marketSchedule = marketScheduleRepository.getSchedule(marketRetailer, LocalDate.now());

        return marketSchedule;
    }

    // 해당 Market의 모든 스케줄 조회
    @Transactional(readOnly = true)
    public List<MarketSchedule> getScheduleOfMarket(Long marketId) {
        return marketScheduleRepository.getScheduleOfMarket(marketId);
    }

    @Transactional
    public MarketSchedule checkAttend(Long marketId, Long retailerId, LocalDate checkDate) {
        MarketRetailer marketRetailer = marketRetailerRepository.getMarketRetailer(marketId, retailerId);
        MarketSchedule marketSchedule = marketScheduleRepository.getSchedule(marketRetailer, checkDate);
        if (marketSchedule == null) {
            throw new CommonException(BError.NOT_EXIST, "MarketSchedule");
        }
        marketSchedule.setCheckAttend(true);
        return marketScheduleRepository.save(marketSchedule);
    }
}
