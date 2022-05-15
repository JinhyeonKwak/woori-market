package com.mayy5.admin.apis;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.req.MarketAgentRequest;
import com.mayy5.admin.model.req.MarketRequest;
import com.mayy5.admin.model.req.RetailerRequest;
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
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @PostMapping(path = "/v1/market", consumes = "application/json")
    ResponseEntity<MarketResponse> createMarket(@RequestBody @Valid MarketRequest marketRequest);

    @ApiOperation(value = "장주 전용 장 리스트 조회 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/markets/market-agent/{userId}")
    List<ResponseEntity<MarketResponse>> getMarketsOfMarketAgent(@PathVariable String userId);

    @ApiOperation(value = "장원 전용 장 리스트 조회 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/markets/retailer/{retailerId}")
    List<ResponseEntity<MarketResponse>> getMarketsOfRetailer(@PathVariable Long retailerId);


    @ApiOperation(value = "장 상세 조회 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/market/{marketId}")
    ResponseEntity<MarketResponse> getMarket(@PathVariable Long marketId);

    @ApiOperation(value = "장 정보 수정 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @PatchMapping(path = "/v1/market/{marketId}", consumes = "application/json")
    ResponseEntity<MarketResponse> updateMarket(@PathVariable Long marketId,
                                                @RequestBody @Valid MarketRequest marketRequest);

    @ApiOperation(value = "등록 장 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketAgentResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @DeleteMapping(path = "/v1/market/{marketId}")
    ResponseEntity<MarketAgentResponse> deleteMarket(@PathVariable Long marketId);

    @ApiOperation(value = "장원 승인 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/market/{retailerId}/{marketId}")
    ResponseEntity<MarketResponse> approveRetailer(@PathVariable Long retailerId, @PathVariable Long marketId);

    @ApiOperation(value = "출석 체크 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = ScheduleResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/market-schedule/{retailerId}/{marketId}")
    ResponseEntity<ScheduleResponse> checkAttend(@PathVariable Long retailerId, @PathVariable Long marketId);


}
