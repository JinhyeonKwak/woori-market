package com.mayy5.admin.model.domain;

import com.mayy5.admin.type.RetailerMetaType;
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
public class Retailer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "RETAILER_ID")
    private Long id;

    private String retailerName;

    private String retailType;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "retailer", orphanRemoval = true)
    private List<MarketRetailer> marketRetailerList = new ArrayList<>();

    @Column(name = "CREATE_AT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "UPDATE_AT")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated(EnumType.STRING)
    @CollectionTable(
            name = "RETAILER_META",
            joinColumns = @JoinColumn(name = "RETAILER_ID")
    )
    @MapKeyColumn(name = "META_TYPE")
    @Column(name = "META_VALUE")
    private Map<RetailerMetaType, String> meta = new HashMap<>();

    //==생성 메서드==//

    public static Retailer createRetailer(User user, Retailer input) {
        Retailer retailer = Retailer.builder()
                .user(user)
                .retailerName(input.getRetailerName())
                .retailType(input.getRetailType())
                .marketRetailerList(new ArrayList<>())
                .meta(input.getMeta())
                .build();
        retailer.setUser(user);
        return retailer;
    }
}
