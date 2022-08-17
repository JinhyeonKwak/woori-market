package com.mayy5.admin.apis;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.res.MarketResponseDto;
import com.mayy5.admin.model.res.RetailerResponseDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Api(value = "MarketClient", tags = SwaggerApiTag.MARKET_CLIENT)
@Validated
public interface MarketClientApi {

    @ApiOperation(value = "사용자 위치 기반 가게 리스트 조회")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = RetailerResponseDto.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/clients/retailers")
    ResponseEntity<List<RetailerResponseDto>> getRetailersAroundClient(@RequestParam Double latitude, @RequestParam Double longitude);

    @ApiOperation(value = "가게 상세 페이지 조회")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = RetailerResponseDto.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/clients/retailers/{retailerId}")
    ResponseEntity<RetailerResponseDto> getRetailerDetail(@PathVariable Long retailerId);

    @ApiOperation(value = "가게의 특정 품목 예약")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = RetailerResponseDto.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/clients/retailers/{retailerId}/items/{itemId}")
    ResponseEntity<RetailerResponseDto> makeReservation(@PathVariable Long retailerId, @PathVariable Long itemId);

    @ApiOperation(value = "장 상세 페이지 조회")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketResponseDto.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/clients/markets/{marketId}")
    ResponseEntity<MarketResponseDto> getMarket(@PathVariable Long marketId);

}
