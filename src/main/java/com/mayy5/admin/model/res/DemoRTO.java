package com.mayy5.admin.model.res;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mayy5.admin.model.dto.Demo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

@ApiModel(value = "데모 데이터")
@Data
@Builder
@JsonSerialize
public class DemoRTO {
	@ApiModelProperty(required = true, value = "데모 id(String)", position = 1)
	@NonNull
	private String id;

	@ApiModelProperty(required = true, value = "내용", position = 2)
	@NonNull
	private String content;

	@ApiModelProperty(value = "읽음 여부", position = 3)
	private Boolean isRead;

	public static DemoRTO fromDemo(Demo demo) {
		return DemoRTO.builder()
			.id(demo.getId())
			.content(demo.getContent())
			.build();
	}
}
