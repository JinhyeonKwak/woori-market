package com.mayy5.admin.model.req;

import com.mayy5.admin.type.MarketAgentMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketAgentRequestDto {


    @ApiModelProperty(value = "MarketAgent Name", example = "KIM")
    private String agentName;

    @ApiModelProperty(value = "Corporate Name", example = "NATURE")
    private String corporateName;

    @ApiModelProperty(
            value = "A MarketAgentPropType map of key/value pairs",
            dataType = "Map[String,String]")
    private Map<MarketAgentMetaType, String> meta = new HashMap<>();

}
