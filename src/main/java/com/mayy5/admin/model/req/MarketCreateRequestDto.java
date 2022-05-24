package com.mayy5.admin.model.req;

import com.mayy5.admin.model.domain.Address;
import com.mayy5.admin.type.MarketAgentMetaType;
import com.mayy5.admin.type.RetailerMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketCreateRequestDto {

    @ApiModelProperty(value = "Market Location")
    private Address address;

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
