package com.mayy5.admin.model.req;

import java.util.HashMap;
import java.util.Map;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import com.mayy5.admin.type.RetailerMetaType;
import com.mayy5.admin.type.RetailerType;

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

	@ApiModelProperty(value = "Market Location")
	@NotBlank
	private String name;

	@ApiModelProperty(value = "Market Location")
	@NotNull
	private RetailerType type;

	@ApiModelProperty(
		value = "A RetailerPropType map of key/value pairs",
		example = "{'ETC' : 'ETC'}",
		dataType = "Map[String,String]")
	private Map<RetailerMetaType, String> meta = new HashMap<>();

}
