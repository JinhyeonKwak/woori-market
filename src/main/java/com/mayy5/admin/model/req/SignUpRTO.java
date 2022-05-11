package com.mayy5.admin.model.req;

import java.util.Map;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import com.mayy5.admin.type.UserMetaType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class SignUpRTO {

	@ApiModelProperty(value = "id", example = "mayy5", position = 1)
	@NotBlank
	private String id;

	@ApiModelProperty(value = "mail", example = "mayy5.master@gmail.com", position = 2)
	@NotBlank
	@Email
	private String email;

	@ApiModelProperty(value = "password", required = true, example = "admin", position = 3)
	@NotBlank
	private String password;

	@ApiModelProperty(value = "name", required = true, example = "mayy5", position = 4)
	@NotBlank
	private String name;

	@ApiModelProperty(value = "phone", required = true, example = "010-0000-0000 ", position = 5)
	@NotBlank
	@Pattern(regexp = "(\\d{3})-(\\d{3,4})-(\\d{4})")
	private String phone;

	@ApiModelProperty(
		value = "A UserPropType map of key/value pairs",
		example = "{'ROLE' : 'ROLE_MARKET_AGENT'}",
		dataType = "Map[String,String]", position = 4)
	private Map<UserMetaType, String> meta;
}
