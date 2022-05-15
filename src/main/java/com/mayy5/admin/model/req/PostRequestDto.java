package com.mayy5.admin.model.req;

import javax.validation.constraints.NotBlank;

import com.mayy5.admin.type.PostType;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PostRequestDto {

	@ApiModelProperty(value = "PostType", required = true, example = "FREE,NOTICE", position = 1)
	private PostType postType;

	@ApiModelProperty(value = "title", required = true, example = "", position = 2)
	@NotBlank
	private String title;

	@ApiModelProperty(value = "title", example = "", position = 3)
	private String subTitle;

	@ApiModelProperty(value = "title", required = true, example = "", position = 4)
	@NotBlank
	private String content;
}
