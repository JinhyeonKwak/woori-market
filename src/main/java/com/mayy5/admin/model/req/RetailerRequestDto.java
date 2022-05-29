package com.mayy5.admin.model.req;

import java.util.HashMap;
import java.util.Map;

import com.mayy5.admin.type.RetailerMetaType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetailerRequestDto {

	@ApiModelProperty(
		value = "A RetailerPropType map of key/value pairs",
		example = "{'BUSINESS_TYPE' : 'FOOD'}",
		dataType = "Map[String,String]")
	private Map<RetailerMetaType, String> meta = new HashMap<>();

}
