package com.mayy5.admin.apis;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.req.CheckAttendDTO;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.req.MarketUpdateRequestDto;
import com.mayy5.admin.model.res.MarketAgentResponseDto;
import com.mayy5.admin.model.res.MarketResponseDto;
import com.mayy5.admin.model.res.RetailerResponseDto;
import com.mayy5.admin.model.res.ScheduleResponseDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value = "Market", tags = SwaggerApiTag.MARKET)
@Validated
public interface MarketApi {

	@ApiOperation(value = "장 등록 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PostMapping(path = "/v1/market", consumes = "application/json")
	ResponseEntity<MarketResponseDto> createMarket(@RequestBody @Valid MarketCreateRequestDto marketCreateRequestDto);

	@ApiOperation(value = "장 상세 조회 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping(path = "/v1/market/{marketId}")
	ResponseEntity<MarketResponseDto> getMarket(@PathVariable Long marketId);

	@ApiOperation(value = "장 정보 수정 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PatchMapping(path = "/v1/market/{marketId}", consumes = "application/json")
	ResponseEntity<MarketResponseDto> updateMarket(@PathVariable Long marketId,
		@RequestBody @Valid MarketUpdateRequestDto marketUpdateRequestDto);

	@ApiOperation(value = "등록 장 삭제 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketAgentResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@DeleteMapping(path = "/v1/market/{marketId}")
	ResponseEntity<MarketAgentResponseDto> deleteMarket(@PathVariable Long marketId);

	@ApiOperation(value = "출석 체크 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = ScheduleResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PatchMapping(path = "/v1/market/{marketId}/schedule/{retailerId}")
	ResponseEntity<ScheduleResponseDto> checkAttend(@RequestBody @Valid CheckAttendDTO checkAttendDTO);

	//==장주 관련==//
	@ApiOperation(value = "특정 장에 대한 장주 등록/변경 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketAgentResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PostMapping(path = "/v1/market/{marketId}/agent/{marketAgentId}")
	ResponseEntity<MarketAgentResponseDto> registerMarketAgent(@PathVariable Long marketId,
		@PathVariable Long marketAgentId);

	@ApiOperation(value = "장주 전용 장 리스트 조회 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping(path = "/v1/markets/agent/{marketAgentId}")
	ResponseEntity<List<MarketResponseDto>> getMarketsOfMarketAgent(@PathVariable Long marketAgentId);

	//==장원 관련==//
	@ApiOperation(value = "특정 장에 대한 장원 등록 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = RetailerResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PostMapping(path = "/v1/market/{marketId}/retailer/{retailerId}")
	ResponseEntity<RetailerResponseDto> registerRetailer(@PathVariable Long marketId, @PathVariable Long retailerId);

	@ApiOperation(value = "특정 장에 대한 장원 탈퇴 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@PatchMapping(path = "/v1/market/{marketId}/retailer/{retailerId}")
	ResponseEntity<MarketResponseDto> dropRetailer(@PathVariable Long marketId, @PathVariable Long retailerId);

	@ApiOperation(value = "장원 전용 장 리스트 조회 API")
	@ApiResponses(value = {
		@ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponseDto.class),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
		@ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
	})
	@GetMapping(path = "/v1/markets/retailer/{retailerId}")
	ResponseEntity<List<MarketResponseDto>> getMarketsOfRetailer(@PathVariable Long retailerId);

}
