package com.mayy5.admin.controller;

import java.util.Arrays;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.mayy5.admin.apis.DemoApi;
import com.mayy5.admin.model.dto.Demo;
import com.mayy5.admin.model.res.DemoRTO;
import com.mayy5.admin.model.res.DemosRTO;

@RestController
public class DemoController implements DemoApi {

	@Override
	public ResponseEntity<DemosRTO> getDemos(
		@PathVariable String userId) {
		Demo demo = Demo.builder().id("000").content("Temp").build();
		DemoRTO demoRTO = DemoRTO.fromDemo(demo);
		return ResponseEntity.ok(DemosRTO.builder()
			.demos(Arrays.asList(demoRTO))
			.build());
	}
}
