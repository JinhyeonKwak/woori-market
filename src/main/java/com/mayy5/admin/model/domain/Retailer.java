package com.mayy5.admin.model.domain;

import com.mayy5.admin.type.RetailType;
import com.mayy5.admin.type.RetailerMetaType;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.HashMap;
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

	@Column(name = "RETAIER_NAME")
	private String retailerName;

	@Enumerated(EnumType.STRING)
	@Column(name = "RETAIER_TYPE")
	private RetailType retailType;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MARKET_ID")
	private Market market;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyEnumerated(EnumType.STRING)
	@CollectionTable(
		name = "RETAILER_META",
		joinColumns = @JoinColumn(name = "RETAILER_ID")
	)
	@MapKeyColumn(name = "META_TYPE")
	@Column(name = "META_VALUE")
	private Map<RetailerMetaType, String> meta = new HashMap<>();

	@Column(name = "CREATE_AT", nullable = false, updatable = false)
	@CreationTimestamp
	private LocalDateTime createAt;

	@Column(name = "UPDATE_AT")
	@UpdateTimestamp
	private LocalDateTime updateAt;

	//==생성 메서드==//
	public static Retailer createRetailer(String name, RetailType retailType, Map<RetailerMetaType, String> meta) {
		Retailer retailer = Retailer.builder()
				.retailerName(name)
				.retailType(retailType)
				.meta(meta)
				.build();
		return retailer;
	}
}
