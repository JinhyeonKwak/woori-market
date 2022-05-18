package com.mayy5.admin.model.req;

import com.mayy5.admin.type.RetailerMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RetailerRequestDto {


    @ApiModelProperty(
            value = "A RetailerPropType map of key/value pairs",
            example = "{'BUSINESS_TYPE' : 'FOOD'}",
            dataType = "Map[String,String]")
    private Map<RetailerMetaType, String> meta = new HashMap<>();

}
