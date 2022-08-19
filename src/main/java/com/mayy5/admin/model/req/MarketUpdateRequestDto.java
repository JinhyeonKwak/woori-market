package com.mayy5.admin.model.req;

import com.mayy5.admin.type.MarketMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class MarketUpdateRequestDto {

    @ApiModelProperty(value = "Market Road Address", example = "서울특별시 종로구 혜화동 20-12")
    private String roadAddress;

    @ApiModelProperty(value = "Market Jibun Address", example = "서울특별시 종로구 혜화로8길 25")
    private String jibunAddress;

    @ApiModelProperty(value = "Start Date")
    private LocalDate startDate;

    @ApiModelProperty(value = "End Date")
    private LocalDate endDate;

    @ApiModelProperty(value = "Market Day")
    private DayOfWeek marketDay;

    @ApiModelProperty(value = "Open At")
    private LocalDateTime openAt;

    @ApiModelProperty(value = "Closed At")
    private LocalDateTime closedAt;

    @ApiModelProperty(
            value = "A MarketPropType map of key/value pairs",
            dataType = "Map[String,String]")
    private Map<MarketMetaType, String> meta = new HashMap<>();
}
