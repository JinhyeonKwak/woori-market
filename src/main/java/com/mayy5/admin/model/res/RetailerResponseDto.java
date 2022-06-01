package com.mayy5.admin.model.res;

import com.mayy5.admin.type.RetailerMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetailerResponseDto {


    @ApiModelProperty(value = "Retailer Name")
    private String retailerName;

    @ApiModelProperty(value = "Retail Type")
    private String retailType;

    @ApiModelProperty(value = "A RetailerPropType map of key/value pairs",
            dataType = "Map[String,String]")
    private Map<RetailerMetaType, String> meta = new HashMap<>();

    @ApiModelProperty(hidden = true)
    private LocalDateTime createAt;

    @ApiModelProperty(hidden = true)
    private LocalDateTime updateAt;
}
