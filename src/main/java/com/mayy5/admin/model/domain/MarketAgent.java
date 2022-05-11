package com.mayy5.admin.model.domain;

import com.mayy5.admin.type.MarketAgentMetaType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MarketAgent {

    @Id
    @GeneratedValue
    @Column(name = "MARKET_AGENT_ID")
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated(EnumType.STRING)
    @CollectionTable(
            name = "MARKET_AGENT_META",
            joinColumns = @JoinColumn(name = "MARKET_AGENT_ID")
    )
    @MapKeyColumn(name = "META_TYPE")
    @Column(name = "META_VALUE")
    private Map<MarketAgentMetaType, String> meta = new HashMap<>();

    @OneToMany(mappedBy = "marketAgent", cascade = CascadeType.ALL)
    private List<Market> marketList = new ArrayList<>();

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "CREATE_AT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "UPDATE_AT")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    //==생성 메서드==//
    public static MarketAgent createMarketAgent(User user, Map<MarketAgentMetaType, String> meta) {
//        MarketAgent marketAgent = MarketAgent.builder().meta(meta).build();
        MarketAgent marketAgent = new MarketAgent();
        marketAgent.setUser(user);
        marketAgent.setMeta(meta);
        return marketAgent;
    }

    //==연관 관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.setMarketAgent(this);
    }



}
