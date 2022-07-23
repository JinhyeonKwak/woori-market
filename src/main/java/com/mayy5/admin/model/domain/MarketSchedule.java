package com.mayy5.admin.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MarketSchedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "SCHEDULE_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_ID")
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RETAILER_ID")
    private Retailer retailer;

    private LocalDate marketDate;

    private Boolean checkAttend;

    //== 생성 메서드 ==//
    public static MarketSchedule createSchedule(Market market, Retailer retailer, LocalDate date) {
        MarketSchedule marketSchedule = MarketSchedule.builder()
                .market(market)
                .retailer(retailer)
                .marketDate(date)
                .checkAttend(false)
                .build();
        return marketSchedule;
    }

}
