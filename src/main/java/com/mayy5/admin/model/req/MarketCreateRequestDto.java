package com.mayy5.admin.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketCreateRequestDto {

    @ApiModelProperty(value = "Market Location")
    private String address;

    @ApiModelProperty(value = "Start Date")
    private LocalDate startDate;

    @ApiModelProperty(value = "End Date")
    private LocalDate endDate;

    @ApiModelProperty(value = "Market Day")
    private DayOfWeek marketDay;

    @ApiModelProperty(value = "MarketAgentRequestDto")
    private MarketAgentRequestDto marketAgentRequestDto;

    @ApiModelProperty(value = "RetailerRequestList")
    private List<RetailerRequestDto> retailerRequestDtoList = new ArrayList<>();
}
