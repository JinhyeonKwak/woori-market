package com.mayy5.admin.model.domain;

import com.mayy5.admin.model.dto.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Retailer {

    @Id @GeneratedValue
    @Column(name = "RETAILER_ID")
    private Long id;

    private String userName;

    private String phoneNumber;

    private String email;

    @Enumerated(EnumType.STRING)
    private Category category;

    @OneToMany(mappedBy = "retailer")
    private List<MarketRetailer> marketRetailerList = new ArrayList<>();

    @OneToMany(mappedBy = "retailer")
    private List<RetailerSchedule> retailerScheduleList = new ArrayList<>();

    //==연관 관계 메서드==//
    public void addMarketRetailer(MarketRetailer marketRetailer) {
        marketRetailerList.add(marketRetailer);
        marketRetailer.setRetailer(this);
    }

    public void addRetailerSchedule(RetailerSchedule retailerSchedule) {
        retailerScheduleList.add(retailerSchedule);
        retailerSchedule.setRetailer(this);

    }


}
