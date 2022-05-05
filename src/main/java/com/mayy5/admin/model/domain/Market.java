package com.mayy5.admin.model.domain;

import com.mayy5.admin.model.dto.MarketDTO;
import lombok.*;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Market {

    @Id @GeneratedValue
    @Column(name = "MARKET_ID")
    private Long id;

    @Embedded
    private Address address;

    @Column(name = "START_AT")
    private LocalDate startDate;

    @Column(name = "END_AT")
    private LocalDate endDate;

    private DayOfWeek marketDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_AGENT_ID")
    private MarketAgent marketAgent;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<MarketRetailer> marketRetailerList = new ArrayList<>();


    //==생성 메서드==//
    public static Market createMarket(MarketAgent marketAgent, MarketDTO marketDTO) {
        Market market = new Market();
        market.setMarketAgent(marketAgent);
        market.setAddress(marketDTO.getAddress());
        market.setStartDate(marketDTO.getStartDate());
        market.setEndDate(marketDTO.getEndDate());
        market.setMarketDay(marketDTO.getMarketDay());

        return market;
    }

    //==연관 관계 메서드==//
    public void setMarketAgent(MarketAgent marketAgent) {
        this.marketAgent = marketAgent;
        marketAgent.getMarketList().add(this);
    }

    public void addRetailer(List<Retailer> retailers) {
        for (Retailer retailer : retailers) {
            MarketRetailer marketRetailer = new MarketRetailer();
            marketRetailer.setMarket(this);
            marketRetailer.setRetailer(retailer);
            marketRetailerList.add(marketRetailer);
        }
    }

}
