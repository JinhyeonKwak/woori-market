package com.mayy5.admin.config;

import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Pageable;

import com.fasterxml.classmate.TypeResolver;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.schema.AlternateTypeRules;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Slf4j
@Configuration
@RequiredArgsConstructor
@EnableSwagger2
public class AdminSwaggerConfig {

	private final TypeResolver typeResolver;
	@Value("${server.ip:localhost}")
	private String ip;
	@Value("${server.port:8080}")
	private int port;

	@Bean
	public Docket api() {
		return new Docket(DocumentationType.SWAGGER_2)
			.apiInfo(apiInfo())
			.host(ip + ":" + port)
			.alternateTypeRules(
				AlternateTypeRules.newRule(typeResolver.resolve(Pageable.class), typeResolver.resolve(Page.class)))
			.securityContexts(Arrays.asList(securityContext()))
			.securitySchemes(Arrays.asList(apiKey()))
			.select()
			.apis(RequestHandlerSelectors.basePackage("com.mayy5.admin.controller"))
			.paths(PathSelectors.any())
			.build();
	}

	private ApiKey apiKey() {
		return new ApiKey("JWT", "Authorization", "header");
	}

	private SecurityContext securityContext() {
		return SecurityContext.builder().securityReferences(defaultAuth()).build();
	}

	private List<SecurityReference> defaultAuth() {
		AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
		AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
		authorizationScopes[0] = authorizationScope;
		return Arrays.asList(new SecurityReference("JWT", authorizationScopes));
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder()
			.title("Admin API")
			.version("0.1")
			.description("Market Api")
			.build();
	}

	@Getter
	@Setter
	@ApiModel
	static class Page {
		@ApiModelProperty(value = "페이지 번호(0..N)", example = "0")
		private Long page;

		@ApiModelProperty(value = "페이지 크기", allowableValues = "range[0, 100]", example = "20")
		private Integer size;

		@ApiModelProperty(value = "정렬(사용법: 컬럼명,ASC|DESC)", example = "id,DESC")
		private List<String> sort;
	}
}
