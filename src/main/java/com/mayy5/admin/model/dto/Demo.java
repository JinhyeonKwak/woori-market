package com.mayy5.admin.model.dto;

import javax.validation.Valid;

import org.hibernate.validator.constraints.NotEmpty;

import lombok.Builder;
import lombok.Value;

/**
 * 데모 모델
 **
 * @author kooru
 */
@Builder
@Value
public class Demo {

	@NotEmpty
	private String id;

	@Valid
	private String content;
}
