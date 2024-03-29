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
public class UserUpdateRequestDto {

	@ApiModelProperty(value = "mail", example = "mayy5.master@gmail.com", position = 2)
	@NotBlank
	@Email
	private String email;

	@ApiModelProperty(value = "password", required = true, example = "admin", position = 3)
	@NotBlank
	@Pattern(regexp = "^(?=.*[0-9])(?=.*[a-z])(?=.*[@#!$%^&+=])(?=\\S+$).{6,16}$", message = "비밀번호 규칙과 동일하지 않습니다.")
	private String password;

	@NotBlank
	@ApiModelProperty(value = "newPassword", required = true, example = "admin", position = 4)
	private String newPassword;

	@ApiModelProperty(value = "name", required = true, example = "010 ", position = 5)
	@NotBlank
	@Pattern(regexp = "(\\d{3})(\\d{3,4})(\\d{4})")
	private String phone;

	@ApiModelProperty(
		value = "A UserPropType map of key/value pairs",
		example = "{'MAIL': 'xxx@yyy.com', 'ROLE' : 'ROLE_MARKET_AGENT'}",
		dataType = "Map[String,String]", position = 6)
	private Map<UserMetaType, String> meta;
}
