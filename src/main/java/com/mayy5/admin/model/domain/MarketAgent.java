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

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MarketAgent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "MARKET_AGENT_ID")
    private Long id;

    private String agentName;

    private String corporateName;

    @OneToMany(mappedBy = "marketAgent")
    private List<Market> marketList = new ArrayList<>();

    @ManyToOne(targetEntity = User.class, fetch = FetchType.LAZY)
    @JoinColumn(name = "USER_ID")
    private User user;

    @Column(name = "CREATE_AT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "UPDATE_AT")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated(EnumType.STRING)
    @CollectionTable(
            name = "MARKET_AGENT_META",
            joinColumns = @JoinColumn(name = "MARKET_AGENT_ID")
    )
    @MapKeyColumn(name = "META_TYPE")
    @Column(name = "META_VALUE")
    private Map<MarketAgentMetaType, String> meta = new HashMap<>();

    //==생성 메서드==//

    public static MarketAgent createMarketAgent(User user, MarketAgent input) {
        MarketAgent marketAgent = MarketAgent.builder()
                .user(user)
                .agentName(input.getAgentName())
                .corporateName(input.getCorporateName())
                .marketList(new ArrayList<>())
                .meta(input.getMeta())
                .build();

        return marketAgent;
    }
}
