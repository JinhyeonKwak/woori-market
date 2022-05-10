package com.mayy5.admin.model.req;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserLoginRTO {

	@ApiModelProperty(value = "admin", required = true, example = "mayy5")
	private String id;
	@ApiModelProperty(value = "admin", required = true, example = "admin")
	private String password;
}
