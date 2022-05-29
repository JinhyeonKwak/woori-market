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
public class ScheduleResponseDto {

	@ApiModelProperty(value = "Retailer ID")
	private Long retailerId;

	@ApiModelProperty(value = "Attendance")
	private Boolean checkAttend;

}
