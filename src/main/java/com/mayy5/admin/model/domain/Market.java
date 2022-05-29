package com.mayy5.admin.model.domain;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Embedded;
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
public class Market {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "MARKET_ID")
	private Long id;

	@Embedded
	private Address address;

	@Column(name = "START_AT")
	private LocalDate startDate;

	@Column(name = "END_AT")
	private LocalDate endDate;

	private DayOfWeek marketDay;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MARKET_AGENT_ID")
	private MarketAgent marketAgent;

	@OneToMany(mappedBy = "market", orphanRemoval = true)
	private List<MarketRetailer> marketRetailerList = new ArrayList<>();

	//==생성 메서드==//
	public static Market createMarket(MarketAgent marketAgent, Market input) {
		Market market = Market.builder()
			.address(input.getAddress())
			.startDate(input.getStartDate())
			.endDate(input.getEndDate())
			.marketDay(input.getMarketDay())
			.marketRetailerList(new ArrayList<>())
			.build();
		market.setMarketAgent(marketAgent);
		return market;
	}

	//==연관 관계 메서드==//
	public void setMarketAgent(MarketAgent marketAgent) {
		this.marketAgent = marketAgent;
		marketAgent.getMarketList().add(this);
	}
}
