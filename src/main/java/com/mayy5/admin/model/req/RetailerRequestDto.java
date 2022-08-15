package com.mayy5.admin.model.req;

import com.mayy5.admin.type.RetailerMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RetailerRequestDto {


    @ApiModelProperty(value = "Retailer Name", example = "KIM")
    private String retailerName;

    @ApiModelProperty(value = "Retail Type", example = "SEAFOOD")
    private String retailType;

    @ApiModelProperty(
            value = "A RetailerPropType map of key/value pairs",
            dataType = "Map[String,String]")
    private Map<RetailerMetaType, String> meta = new HashMap<>();

}
