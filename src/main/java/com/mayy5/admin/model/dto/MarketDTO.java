package com.mayy5.admin.model.dto;

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.mayy5.admin.model.domain.Address;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketDTO {

	private Address address;
	private LocalDate startDate;
	private LocalDate endDate;
	private DayOfWeek marketDay;
}
