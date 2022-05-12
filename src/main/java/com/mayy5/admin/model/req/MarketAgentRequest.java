package com.mayy5.admin.model.req;

import com.mayy5.admin.model.domain.User;
import com.mayy5.admin.type.MarketAgentMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketAgentRequest {

    @ApiModelProperty(value = "User ID", position = 1)
    @NotBlank
    private String userId;

    @ApiModelProperty(
            value = "A MarketAgentPropType map of key/value pairs",
            example = "{'CORPORATE_NAME' : 'NATURE'}",
            dataType = "Map[String,String]",
            position = 2)
    private Map<MarketAgentMetaType, String> meta;

}
