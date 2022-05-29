package com.mayy5.admin.model.domain;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MarketRetailer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MARKET_RETAILER_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MARKET_ID")
	private Market market;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "RETAILER_ID")
	private Retailer retailer;

	@OneToMany(mappedBy = "marketRetailer")
	private List<MarketSchedule> marketScheduleList = new ArrayList<>();

	public static MarketRetailer createMarketRetailer(Market market, Retailer retailer) {
		MarketRetailer marketRetailer = MarketRetailer.builder()
			.market(market)
			.retailer(retailer)
			.marketScheduleList(new ArrayList<>())
			.build();

		// 연관 관계
		market.getMarketRetailerList().add(marketRetailer);
		retailer.getMarketRetailerList().add(marketRetailer);
		return marketRetailer;
	}

}