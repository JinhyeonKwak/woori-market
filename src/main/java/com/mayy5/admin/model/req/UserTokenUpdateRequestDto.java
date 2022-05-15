package com.mayy5.admin.model.req;

import javax.validation.constraints.NotBlank;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserTokenUpdateRequestDto {

	@ApiModelProperty(value = "", required = true, example = "jwt token")
	@NotBlank
	private String refreshToken;
}
