package com.mayy5.admin.model.res;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ScheduleResponse {

    @ApiModelProperty(value = "Retailer ID", position = 1)
    private Long retailerId;

    @ApiModelProperty(value = "Attendance", position = 2)
    private Boolean checkAttend;

}
