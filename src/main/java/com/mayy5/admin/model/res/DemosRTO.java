package com.mayy5.admin.model.res;

import java.util.List;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@ApiModel(value = "데모 조회 결과")
@Data
@Builder
@JsonSerialize
@AllArgsConstructor
public class DemosRTO {
	@ApiModelProperty(required = true, position = 1, value = "데모 목록")
	@NonNull
	private List<DemoRTO> demos;
}
