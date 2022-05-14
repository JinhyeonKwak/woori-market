package com.mayy5.admin.model.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class UserLoginRequestDto {

	@ApiModelProperty(value = "admin", required = true, example = "mayy5")
	@NotBlank
	private String id;

	@ApiModelProperty(value = "admin", required = true, example = "admin")
	@NotBlank
	private String password;
}
