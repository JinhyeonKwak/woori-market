package com.mayy5.admin.model.domain;

import lombok.*;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MarketRetailer {

    @Id
    @GeneratedValue
    @Column(name = "MARKET_RETAILER_ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "MARKET_ID")
    private Market market;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "RETAILER_ID")
    private Retailer retailer;

    @OneToMany(mappedBy = "marketRetailer", cascade = CascadeType.ALL)
    private List<Schedule> scheduleList = new ArrayList<>();

}