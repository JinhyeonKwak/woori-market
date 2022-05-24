package com.mayy5.admin.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CheckAttendDTO {

    @ApiModelProperty(value = "Market ID")
    private Long marketId;

    @ApiModelProperty(value = "Retailer ID")
    private Long retailerId;

    @ApiModelProperty(value = "Check Date")
    private LocalDate checkDate;
}
