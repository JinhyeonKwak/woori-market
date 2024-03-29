package com.mayy5.admin.model.req;

import com.mayy5.admin.type.MarketMetaType;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MarketCreateRequestDto {

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

    @ApiModelProperty(value = "Open At", example = "9")
    private String openAt;

    @ApiModelProperty(value = "Closed At", example = "20")
    private String closedAt;

    @ApiModelProperty(value = "MarketAgentRequestDto")
    private MarketAgentRequestDto marketAgent;

    @ApiModelProperty(value = "RetailerRequestList")
    private List<RetailerRequestDto> retailers = new ArrayList<>();

    @ApiModelProperty(
            value = "A MarketPropType map of key/value pairs",
            dataType = "Map[String,String]")
    private Map<MarketMetaType, String> meta = new HashMap<>();
}
