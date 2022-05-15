package com.mayy5.admin.model.req;

import com.mayy5.admin.model.domain.Address;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketRequest {

    @ApiModelProperty(value = "MarketAgent ID", position = 1)
    private Long marketAgentId;

    @ApiModelProperty(value = "Market Location", position = 2)
    private Address address;

    @ApiModelProperty(value = "Start Date", position = 3)
    private LocalDate startDate;

    @ApiModelProperty(value = "End Date", position = 4)
    private LocalDate endDate;

    @ApiModelProperty(value = "Market Day", position = 5)
    private DayOfWeek marketDay;

}
