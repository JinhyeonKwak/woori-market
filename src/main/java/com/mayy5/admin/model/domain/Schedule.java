package com.mayy5.admin.model.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Schedule {

    @Id
    @GeneratedValue
    @Column(name = "SCHEDULE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_RETAILER_ID")
    private MarketRetailer marketRetailer;

    private LocalDate marketDate;

    private Boolean checkAttend;


    //==생성 메서드==//
    public static Schedule createSchedule(MarketRetailer marketRetailer) {
        Schedule schedule = Schedule.builder()
                        .checkAttend(false) // default : 결석
                        .marketDate(LocalDate.now())
                        .build();
        schedule.setMarketRetailer(marketRetailer);
        return schedule;
    }

    //==연관 관계 메서드==//
    public void setMarketRetailer(MarketRetailer marketRetailer) {
        this.marketRetailer = marketRetailer;
        marketRetailer.getScheduleList().add(this);
    }



}
