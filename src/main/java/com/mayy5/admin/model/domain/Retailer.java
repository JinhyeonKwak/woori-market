package com.mayy5.admin.model.domain;

import com.mayy5.admin.type.RetailSubtype;
import com.mayy5.admin.type.RetailType;
import com.mayy5.admin.type.RetailerMetaType;
import lombok.*;

import javax.persistence.*;
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
public class Retailer extends BaseTime {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "RETAILER_ID")
	private Long id;

	@Column(name = "RETAILER_NAME")
	private String retailerName;

	@Enumerated(EnumType.STRING)
	@Column(name = "RETAIL_TYPE")
	private RetailType retailType;

	@ElementCollection
	@CollectionTable(
			name = "RETAIL_SUBTYPE",
			joinColumns = @JoinColumn(name = "RETAILER_ID")
	)
	@Enumerated(EnumType.STRING)
	@Column(name = "SUBTYPE_VALUE")
	private List<RetailSubtype> retailSubtypeList = new ArrayList<>();

	@Column(name = "START_AT")
	private String startAt;

	@Column(name = "END_AT")
	private String endAt;

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
	public static Retailer createRetailer(Retailer inputRetailer) {
		Retailer retailer = Retailer.builder()
				.retailerName(inputRetailer.getRetailerName())
				.retailType(inputRetailer.getRetailType())
				.retailSubtypeList(inputRetailer.getRetailSubtypeList())
				.startAt(inputRetailer.getStartAt())
				.endAt(inputRetailer.getEndAt())
				.meta(inputRetailer.getMeta())
				.build();
		return retailer;
	}
}
