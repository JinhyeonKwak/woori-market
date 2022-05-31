package com.mayy5.admin.model.res;

import com.mayy5.admin.type.MarketMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketResponseDto {

    @ApiModelProperty(value = "Market ID")
    private Long marketId;

    @ApiModelProperty(value = "Market Location")
    private String address;

    @ApiModelProperty(value = "Market Location AreaCode")
    private String areaCode;

    @ApiModelProperty(value = "Market Location Latitude")
    private String latitude;

    @ApiModelProperty(value = "Market Location longitude")
    private String longitude;

    @ApiModelProperty(value = "Start Date")
    private LocalDate startDate;

    @ApiModelProperty(value = "End Date")
    private LocalDate endDate;

    @ApiModelProperty(value = "Market Day")
    private DayOfWeek marketDay;

    @ApiModelProperty(
            value = "A MarketPropType map of key/value pairs",
            dataType = "Map[String,String]"
    )
    private Map<MarketMetaType, String> meta;

}
