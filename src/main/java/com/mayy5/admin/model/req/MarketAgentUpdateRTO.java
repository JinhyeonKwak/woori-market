package com.mayy5.admin.model.req;

import com.mayy5.admin.type.MarketAgentMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
public class MarketAgentUpdateRTO {

    @ApiModelProperty(
            value = "A MarketAgentPropType map of key/value pairs",
            example = "",
            dataType = "Map[String,String]")
    private Map<MarketAgentMetaType, String> meta;
}
