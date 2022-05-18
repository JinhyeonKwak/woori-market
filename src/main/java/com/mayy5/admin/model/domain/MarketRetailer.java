package com.mayy5.admin.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MarketRetailer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARKET_RETAILER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_ID")
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RETAILER_ID")
    private Retailer retailer;

    @OneToMany(mappedBy = "marketRetailer", cascade = CascadeType.PERSIST)
    private List<Schedule> scheduleList = new ArrayList<>();

    public static MarketRetailer createMarketRetailer(Market market, Retailer retailer) {
        MarketRetailer marketRetailer = MarketRetailer.builder()
                .market(market)
                .retailer(retailer)
                .scheduleList(new ArrayList<>())
                .build();
        return marketRetailer;
    }

}