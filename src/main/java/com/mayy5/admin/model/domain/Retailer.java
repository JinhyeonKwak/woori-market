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

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Retailer {

    @Id
    @GeneratedValue
    @Column(name = "RETAILER_ID")
    private Long id;

    @ElementCollection(fetch = FetchType.EAGER)
    @MapKeyEnumerated(EnumType.STRING)
    @CollectionTable(
            name = "RETAILER_META",
            joinColumns = @JoinColumn(name = "RETAILER_ID")
    )
    @MapKeyColumn(name = "META_TYPE")
    @Column(name = "META_VALUE")
    private Map<RetailerMetaType, String> meta = new HashMap<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "USER_ID")
    private User user;

    @OneToMany(mappedBy = "retailer")
    private List<MarketRetailer> marketRetailerList = new ArrayList<>();

    @Column(name = "CREATE_AT", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createAt;

    @Column(name = "UPDATE_AT")
    @UpdateTimestamp
    private LocalDateTime updateAt;

    //==생성 메서드==//
    public static Retailer createRetailer(User user, Map<RetailerMetaType, String> meta) {
        Retailer retailer = Retailer.builder().meta(meta).build();
        retailer.setUser(user);
        return retailer;
    }

    //==연관 관계 메서드==//
    public void setUser(User user) {
        this.user = user;
        user.getRetailerList().add(this);
    }
}
