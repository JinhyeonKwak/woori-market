package com.mayy5.admin.model.res;

import com.mayy5.admin.type.MarketAgentMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketAgentResponseDto {


    @ApiModelProperty(
            value = "A MarketAgentPropType map of key/value pairs",
            example = "",
            dataType = "Map[String,String]")
    private Map<MarketAgentMetaType, String> meta;

    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt;

    @ApiModelProperty(hidden = true)
    private LocalDateTime updateAt;

}
