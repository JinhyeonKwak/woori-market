package com.mayy5.admin.apis;

import com.mayy5.admin.apis.type.SwaggerApiTag;
import com.mayy5.admin.model.req.MarketAgentCreateRTO;
import com.mayy5.admin.model.req.MarketAgentUpdateRTO;
import com.mayy5.admin.model.res.MarketAgentRTO;
import com.mayy5.admin.model.res.UserRTO;
import io.swagger.annotations.Api;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;

@Api(value = "MarketAgent", tags = SwaggerApiTag.MARKET_AGENT)
@Validated
public interface MarketAgentApi {

    @PostMapping(path = "/v1/market-agent", consumes = "application/json")
    ResponseEntity<MarketAgentRTO> createMarketAgent(@RequestBody @Valid MarketAgentCreateRTO marketAgentCreateRTO);

    @GetMapping(path = "/v1/market-agents")
    ResponseEntity<List<MarketAgentRTO>> getMarketAgentList(@RequestBody @Valid String userId);

    @GetMapping(path = "/v1/market-agent/{marketAgentId}")
    ResponseEntity<MarketAgentRTO> getMarketAgent(@PathVariable Long marketAgentId);

    @PatchMapping(path = "/v1/market-agent/{marketAgentId}", consumes = "application/json")
    ResponseEntity<MarketAgentRTO> updateMarketAgent(@PathVariable Long marketAgentId,
                                                     @RequestBody @Valid MarketAgentUpdateRTO marketAgentUpdateRTO);

    @DeleteMapping(path = "/v1/market-agent/{marketAgentId}")
    ResponseEntity<UserRTO> deleteMarketAgent(@PathVariable Long marketAgentId);
}
