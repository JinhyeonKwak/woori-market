package com.mayy5.admin.model.domain;

import com.mayy5.admin.type.RetailType;
import com.mayy5.admin.type.RetailerMetaType;
import lombok.*;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Retailer extends BaseTime {

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
