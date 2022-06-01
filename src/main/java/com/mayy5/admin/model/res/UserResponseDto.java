package com.mayy5.admin.model.res;

import java.time.LocalDateTime;
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
public class UserResponseDto {

	@ApiModelProperty(value = "User ID", example = "admin", position = 1)
	private String id;

	@ApiModelProperty(value = "mail", example = "mayy5.master@gmail.com", position = 2)
	@NotBlank
	@Email
	private String email;

	@NotBlank
	private String name;

	@NotBlank
	@Pattern(regexp = "(\\d{3})-(\\d{3,4})-(\\d{4})")
	private String phone;

	@ApiModelProperty(
		value = "A UserPropType map of key/value pairs",
		example = "{'MAIL': 'xxx@yyy.com', 'ROLE' : 'ROLE_MARKET_AGENT'}",
		dataType = "Map[String,String]", position = 4)
	private Map<UserMetaType, String> meta;

	@ApiModelProperty(hidden = true)
	private LocalDateTime createAt;

	@ApiModelProperty(hidden = true)
	private LocalDateTime updateAt;

}
