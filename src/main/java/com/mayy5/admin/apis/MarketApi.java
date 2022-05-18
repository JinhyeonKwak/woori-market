package com.mayy5.admin.apis;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.req.MarketRequestDto;
import com.mayy5.admin.model.res.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

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
    ResponseEntity<MarketResponseDto> createMarket(@RequestBody @Valid MarketRequestDto marketRequest);

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
                                                   @RequestBody @Valid MarketRequestDto marketRequest);

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
    @GetMapping(path = "/v1/market/{marketId}/schedule/{retailerId}")
    ResponseEntity<ScheduleResponseDto> checkAttend(@PathVariable Long retailerId, @PathVariable Long marketId);


    //==장주 관련==//
    @ApiOperation(value = "특정 장에 대한 장주 등록/변경 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketAgentResponseDto.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @PostMapping(path = "/v1/market/{marketId}/agent/{marketAgentId}")
    ResponseEntity<MarketAgentResponseDto> registerMarketAgent(@PathVariable Long marketId, @PathVariable Long marketAgentId);

    @ApiOperation(value = "장주 전용 장 리스트 조회 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponseDto.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/markets/{marketAgentId}/agent")
    List<ResponseEntity<MarketResponseDto>> getMarketsOfMarketAgent(@PathVariable Long marketAgentId);

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
    List<ResponseEntity<MarketResponseDto>> getMarketsOfRetailer(@PathVariable Long retailerId);



}
