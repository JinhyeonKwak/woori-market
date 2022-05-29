package com.mayy5.admin.model.req;

import java.util.Map;

import com.mayy5.admin.type.MarketAgentMetaType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketAgentRequestDto {

	@ApiModelProperty(
		value = "A MarketAgentPropType map of key/value pairs",
		example = "{'CORPORATE_NAME' : 'NATURE'}",
		dataType = "Map[String,String]")
	private Map<MarketAgentMetaType, String> meta;

}
