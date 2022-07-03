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

    private String locationAddress;
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

    @OneToMany(mappedBy = "market", orphanRemoval = true)
    private List<MarketRetailer> marketRetailerList = new ArrayList<>();

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
    public static Market createMarket(MarketAgent marketAgent, Market input) {
        Market market = Market.builder()
                .locationAddress(input.getLocationAddress())
                .detailAddress(input.getDetailAddress())
                .regionCode(input.getRegionCode())
                .latitude(input.getLatitude())
                .longitude(input.getLongitude())
                .startDate(input.getStartDate())
                .endDate(input.getEndDate())
                .marketDay(input.getMarketDay())
                .marketRetailerList(new ArrayList<>())
                .meta(input.getMeta())
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
