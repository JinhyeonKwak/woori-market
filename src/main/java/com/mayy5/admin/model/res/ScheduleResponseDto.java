package com.mayy5.admin.model.res;

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
public class ScheduleResponseDto {

    @ApiModelProperty(value = "Market ID")
    private Long marketId;

    @ApiModelProperty(value = "Retailer ID")
    private Long retailerId;

    @ApiModelProperty(value = "Market Date")
    private LocalDate marketDate;

    @ApiModelProperty(value = "Attendance")
    private Boolean checkAttend;

}
