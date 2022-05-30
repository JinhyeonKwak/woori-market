package com.mayy5.admin.model.domain;

import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARKET_ID")
    private Long id;

    private String areaCode;
    private String latitude;
    private String longitude;

    @Column(name = "START_AT")
    private LocalDate startDate;

    @Column(name = "END_AT")
    private LocalDate endDate;


    private DayOfWeek marketDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_AGENT_ID")
    private MarketAgent marketAgent;

    @OneToMany(mappedBy = "market", orphanRemoval = true)
    private List<MarketRetailer> marketRetailerList = new ArrayList<>();


    //==생성 메서드==//
    public static Market createMarket(MarketAgent marketAgent, Market input) {
        Market market = Market.builder()
                .areaCode(input.getAreaCode())
                .latitude(input.getLatitude())
                .longitude(input.getLongitude())
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .marketDay(input.getMarketDay())
                .marketRetailerList(new ArrayList<>())
                .build();

        market.setMarketAgent(marketAgent);
        return market;
    }

    //==연관 관계 메서드==//
    public void setMarketAgent(MarketAgent marketAgent) {
        this.marketAgent = marketAgent;
        marketAgent.getMarketList().add(this);
    }
}
