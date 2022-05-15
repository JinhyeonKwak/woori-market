package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.Schedule;
import com.mayy5.admin.model.dto.MarketDTO;
import com.mayy5.admin.model.req.MarketRequestDto;
import com.mayy5.admin.model.res.MarketResponseDto;
import com.mayy5.admin.model.res.ScheduleResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MarketMapper {

    MarketMapper marketMapper = Mappers.getMapper(MarketMapper.class);

    @Mapping(source = "address", target = "address")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "marketDay", target = "marketDay")
    MarketDTO toMarketDTO(MarketRequestDto dto);

    @Mapping(source = "id", target = "marketId")
    @Mapping(source = "address", target = "address")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "marketDay", target = "marketDay")
    MarketResponseDto toMarketResponse(Market market);

    @Mapping(source = "address", target = "address")
    @Mapping(source = "startDate", target = "startDate")
    @Mapping(source = "endDate", target = "endDate")
    @Mapping(source = "marketDay", target = "marketDay")
    void update(MarketRequestDto marketRequest, @MappingTarget Market market);

    @Mapping(source = "marketRetailer.retailer.id", target = "retailerId")
    @Mapping(source = "checkAttend", target = "checkAttend")
    ScheduleResponseDto toScheduleResponse(Schedule schedule);
}
