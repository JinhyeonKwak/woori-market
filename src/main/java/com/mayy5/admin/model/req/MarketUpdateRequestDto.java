package com.mayy5.admin.model.req;

import java.time.DayOfWeek;
import java.time.LocalDate;

import com.mayy5.admin.model.domain.Address;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketUpdateRequestDto {

	@ApiModelProperty(value = "Market Location")
	private Address address;

	@ApiModelProperty(value = "Start Date")
	private LocalDate startDate;

	@ApiModelProperty(value = "End Date")
	private LocalDate endDate;

	@ApiModelProperty(value = "Market Day")
	private DayOfWeek marketDay;

}
