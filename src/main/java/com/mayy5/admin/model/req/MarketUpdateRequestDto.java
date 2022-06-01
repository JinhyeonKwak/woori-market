package com.mayy5.admin.model.req;

import com.mayy5.admin.type.MarketMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketUpdateRequestDto {

    @ApiModelProperty(value = "Market Location Address", example = "서울특별시 종로구 혜화동 20-12")
    private String locationAddress;

    @ApiModelProperty(value = "Market Detail Address", example = "혜화훼미리 아파트 1동 401호")
    private String detailAddress;

    @ApiModelProperty(value = "Start Date")
    private LocalDate startDate;

    @ApiModelProperty(value = "End Date")
    private LocalDate endDate;

    @ApiModelProperty(value = "Market Day")
    private DayOfWeek marketDay;

    @ApiModelProperty(
            value = "A MarketPropType map of key/value pairs",
            dataType = "Map[String,String]")
    private Map<MarketMetaType, String> meta = new HashMap<>();
}
