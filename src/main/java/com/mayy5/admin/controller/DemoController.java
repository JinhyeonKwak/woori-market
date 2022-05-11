package com.mayy5.admin.controller;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mayy5.admin.apis.DemoApi;
import com.mayy5.admin.model.dto.Demo;
import com.mayy5.admin.model.res.DemoResponseDto;
import com.mayy5.admin.model.res.DemosResponseDto;

@RestController
public class DemoController implements DemoApi {

	@Override
	public ResponseEntity<DemosResponseDto> getDemos(
		@PathVariable String userId) {
		Demo demo = Demo.builder().id("000").content("Temp").build();
		DemoResponseDto demoResponseDto = DemoResponseDto.fromDemo(demo);
		return ResponseEntity.ok(DemosResponseDto.builder()
			.demos(Arrays.asList(demoResponseDto))
			.build());
	}
}
