package com.mayy5.admin.model.domain;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class MarketSchedule {

	@Id
	@GeneratedValue
	@Column(name = "SCHEDULE_ID")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MARKET_RETAILER_ID")
	private MarketRetailer marketRetailer;

	private LocalDate marketDate;

	private Boolean checkAttend;

	//==생성 메서드==//
	public static MarketSchedule createSchedule(MarketRetailer marketRetailer, LocalDate marketDate) {
		MarketSchedule marketSchedule = MarketSchedule.builder()
			.checkAttend(false) // default : 결석
			.marketDate(marketDate)
			.build();
		marketSchedule.setMarketRetailer(marketRetailer);
		return marketSchedule;
	}

	//==연관 관계 메서드==//
	public void setMarketRetailer(MarketRetailer marketRetailer) {
		this.marketRetailer = marketRetailer;
		marketRetailer.getMarketScheduleList().add(this);
	}

}
