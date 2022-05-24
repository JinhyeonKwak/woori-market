package com.mayy5.admin.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MarketSchedule {

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
    public static MarketSchedule createSchedule(MarketRetailer marketRetailer) {
        MarketSchedule marketSchedule = MarketSchedule.builder()
                        .checkAttend(false) // default : 결석
                        .marketDate(LocalDate.now())
                        .build();
        marketSchedule.setMarketRetailer(marketRetailer);
        return marketSchedule;
    }

    //==연관 관계 메서드==//
    public void setMarketRetailer(MarketRetailer marketRetailer) {
        this.marketRetailer = marketRetailer;
        marketRetailer.getMarketScheduleList().add(this);
    }



}
