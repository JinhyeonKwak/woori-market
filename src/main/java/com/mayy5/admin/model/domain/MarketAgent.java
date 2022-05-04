package com.mayy5.admin.model.domain;

import com.mayy5.admin.model.dto.User;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class MarketAgent {

    @Id
    @GeneratedValue
    @Column(name = "market_agent_id")
    private Long id;

    private String userName;

    private String email;

    private String phoneNumber;

    private String corporateName;

    @OneToMany(mappedBy = "marketAgent", cascade = CascadeType.ALL)
    private List<Market> marketList = new ArrayList<>();


}
