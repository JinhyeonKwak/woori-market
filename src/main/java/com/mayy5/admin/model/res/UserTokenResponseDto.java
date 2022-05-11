package com.mayy5.admin.model.res;

import java.util.Map;

import com.mayy5.admin.type.UserMetaType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class UserTokenResponseDto {

	private String accessToken;

	private String refreshToken;

	// 만일 필요한 정보가 있다면 여기 담아서 전달한다.
	@ApiModelProperty(hidden = true)
	private Map<UserMetaType, String> meta;
}
