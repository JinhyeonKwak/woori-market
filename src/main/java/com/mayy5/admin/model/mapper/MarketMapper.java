package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.req.MarketUpdateRequestDto;
import com.mayy5.admin.model.res.MarketResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MarketMapper {

    MarketMapper marketMapper = Mappers.getMapper(MarketMapper.class);

    Market toEntity(MarketCreateRequestDto dto);

    void update(MarketUpdateRequestDto updateRequest, @MappingTarget Market market);

    @Mapping(source = "id", target = "marketId")
    MarketResponseDto toMarketResponse(Market market);

    default List<MarketResponseDto> toMarketResponseList(List<Market> marketList) {
        return marketList.stream()
                .map(marketMapper::toMarketResponse)
                .collect(Collectors.toList());
    }

//    @Mapping(source = "marketRetailer.retailer.id", target = "retailerId")
//    @Mapping(source = "marketRetailer.market.id", target = "marketId")
//    ScheduleResponseDto toScheduleResponse(MarketSchedule marketSchedule);

}
