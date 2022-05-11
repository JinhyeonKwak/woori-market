package com.mayy5.admin.apis;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.req.MarketAgentRequest;
import com.mayy5.admin.model.res.MarketAgentResponse;
import com.mayy5.admin.model.res.UserResponseDto;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@Api(value = "MarketAgent", tags = SwaggerApiTag.MARKET_AGENT)
@Validated
public interface MarketAgentApi {

    @PostMapping(path = "/v1/market-agent", consumes = "application/json")
    ResponseEntity<MarketAgentResponse> createMarketAgent(@RequestBody @Valid MarketAgentRequest marketAgentRequest);

    @GetMapping(path = "/v1/market-agent", consumes = "application/json")
    ResponseEntity<MarketAgentResponse> getMarketAgent(@RequestBody @Valid String userId);

    @PatchMapping(path = "/v1/market-agent", consumes = "application/json")
    ResponseEntity<MarketAgentResponse> updateMarketAgent(@RequestBody @Valid MarketAgentRequest marketAgentRequest);

    @DeleteMapping(path = "/v1/market-agent", consumes = "application/json")
    ResponseEntity<UserResponseDto> deleteMarketAgent(@RequestBody @Valid String userId);


}
