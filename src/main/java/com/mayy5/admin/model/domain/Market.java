package com.mayy5.admin.model.domain;

import com.mayy5.admin.model.dto.MarketDTO;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
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

    @ElementCollection
    @CollectionTable(
            name = "OPEN_DAY",
            joinColumns = @JoinColumn(name = "MARKET_DAY_ID")
    )
    @OrderColumn(name = "LIST_IDX")
    @Column(name = "MARKET_DAY")
    private List<DayOfWeek> marketDay = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_AGENT_ID")
    private MarketAgent marketAgent;

    @OneToMany(mappedBy = "market", cascade = CascadeType.ALL)
    private List<MarketRetailer> marketRetailerList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "SCHEDULE_ID")
    private Schedule schedule;


    //==생성 메서드==//
    /**
     * 장 등록
     */
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

    public void setSchedule(Schedule schedule) {
        this.schedule = schedule;
        schedule.setMarket(this);
    }

}
