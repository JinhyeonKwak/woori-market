package com.mayy5.admin.model.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Getter @Setter
public class MarketRetailer {

    @Id @GeneratedValue
    @Column(name = "MARKET_RETAILER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_ID")
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RETAILER_ID")
    private Retailer retailer;

}
