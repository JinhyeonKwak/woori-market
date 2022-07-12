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
public class Market {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARKET_ID")
    private Long id;

    private String roadAddress;
    private String jibunAddress;
    private String detailAddress;
    private String regionCode;
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

    @OneToMany(mappedBy = "market")
    private List<Retailer> retailerList = new ArrayList<>();

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
    public static Market createMarket(MarketAgent marketAgent, Market inputMarket, List<Retailer> retailerList) {
        Market market = Market.builder()
                .roadAddress(inputMarket.getRoadAddress())
                .jibunAddress(inputMarket.getJibunAddress())
                .detailAddress(inputMarket.getDetailAddress())
                .regionCode(inputMarket.getRegionCode())
                .latitude(inputMarket.getLatitude())
                .longitude(inputMarket.getLongitude())
                .startDate(inputMarket.getStartDate())
                .endDate(inputMarket.getEndDate())
                .marketDay(inputMarket.getMarketDay())
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
}
