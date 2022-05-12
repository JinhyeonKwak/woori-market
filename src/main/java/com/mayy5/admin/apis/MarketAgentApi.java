package com.mayy5.admin.apis;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.dto.User;
import com.mayy5.admin.model.req.MarketAgentRequest;
import com.mayy5.admin.model.res.MarketAgentResponse;
import com.mayy5.admin.model.res.UserRTO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "MarketAgent", tags = SwaggerApiTag.MARKET_AGENT)
@Validated
public interface MarketAgentApi {

    @ApiOperation(value = "장주 등록 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketAgentResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @PostMapping(path = "/v1/market-agent", consumes = "application/json")
    ResponseEntity<MarketAgentResponse> createMarketAgent(@RequestBody @Valid MarketAgentRequest marketAgentRequest);

    @ApiOperation(value = "장주 조회 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketAgentResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @GetMapping(path = "/v1/market-agent/{userId}")
    ResponseEntity<MarketAgentResponse> getMarketAgent(@PathVariable String userId);

    @ApiOperation(value = "장주 정보 수정 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = MarketAgentResponse.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @PatchMapping(path = "/v1/market-agent", consumes = "application/json")
    ResponseEntity<MarketAgentResponse> updateMarketAgent(@RequestBody @Valid MarketAgentRequest marketAgentRequest);

    @ApiOperation(value = "등록 장주 삭제 API")
    @ApiResponses(value = {
            @ApiResponse(code = org.apache.http.HttpStatus.SC_OK, message = "성공", response = UserRTO.class),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_NOT_IMPLEMENTED, message = "아직 제공하지 않는 기능"),
            @ApiResponse(code = org.apache.http.HttpStatus.SC_BAD_REQUEST, message = "잘못된 요청")
    })
    @DeleteMapping(path = "/v1/market-agent/{userId}")
    ResponseEntity<UserRTO> deleteMarketAgent(@PathVariable String userId);


}
