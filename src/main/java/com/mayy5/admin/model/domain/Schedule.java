package com.mayy5.admin.model.domain;

import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

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

    @Column(name = "CREATE_AT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDate createAt;

    @Enumerated(EnumType.STRING)
    private CheckAttend checkAttend;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_RETAILER_ID")
    private MarketRetailer marketRetailer;

    //==생성 메서드==//
    public static Schedule createSchedule(MarketRetailer marketRetailer) {
        Schedule schedule = new Schedule();
        schedule.setCheckAttend(CheckAttend.PRESENT);
        schedule.setMarketRetailer(marketRetailer);
        return schedule;
    }

    //==연관 관계 메서드==//
    public void setMarketRetailer(MarketRetailer marketRetailer) {
        this.marketRetailer = marketRetailer;
        marketRetailer.getScheduleList().add(this);
    }



}
