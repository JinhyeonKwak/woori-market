package com.mayy5.admin.model.domain;

import com.mayy5.admin.type.MarketMetaType;
import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Market extends BaseTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARKET_ID")
    private Long id;

    @Embedded
    private Address address;

    @Column(name = "START_AT")
    private LocalDate startDate;

    @Column(name = "END_AT")
    private LocalDate endDate;

    private DayOfWeek marketDay;

    @Column(name = "OPEN_AT")
    private String openAt;

    @Column(name = "CLOSED_AT")
    private String closedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_AGENT_ID")
    private MarketAgent marketAgent;

    @OneToMany(mappedBy = "market", cascade = CascadeType.REMOVE)
    private List<Retailer> retailerList = new ArrayList<>();

    @OneToMany(mappedBy = "market")
    private List<MarketSchedule> marketScheduleList = new ArrayList<>();

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated(EnumType.STRING)
    @CollectionTable(
            name = "MARKET_META",
            joinColumns = @JoinColumn(name = "MARKET_ID")
    )
    @MapKeyColumn(name = "META_TYPE")
    @Column(name = "META_VALUE")
    private Map<MarketMetaType, String> meta = new HashMap<>();


    //==생성 메서드==//
    public static Market createMarket(Market inputMarket, Address address, MarketAgent marketAgent, List<Retailer> retailerList) {
        Market market = Market.builder()
                .address(address)
                .startDate(inputMarket.getStartDate())
                .endDate(inputMarket.getEndDate())
                .marketDay(inputMarket.getMarketDay())
                .openAt(inputMarket.getOpenAt())
                .closedAt(inputMarket.getClosedAt())
                .meta(inputMarket.getMeta())
                .build();
        market.setMarketAgent(marketAgent);
        market.setRetailerList(retailerList);
        return market;
    }

    //==연관 관계 메서드==//
    public void setMarketAgent(MarketAgent marketAgent) {
        this.marketAgent= marketAgent;
        marketAgent.getMarketList().add(this);
    }

    public void setRetailerList(List<Retailer> retailerList) {
        this.retailerList = retailerList;
        retailerList.forEach(retailer -> retailer.setMarket(this));
    }

    public void addRetailerList(List<Retailer> retailerList) {
        retailerList.forEach(retailer -> {
            this.getRetailerList().add(retailer);
            retailer.setMarket(this);
        });
    }
}
