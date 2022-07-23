package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.Address;
import com.mayy5.admin.model.domain.Market;
import com.mayy5.admin.model.domain.MarketSchedule;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.req.MarketUpdateRequestDto;
import com.mayy5.admin.model.res.MarketResponseDto;
import com.mayy5.admin.model.res.ScheduleResponseDto;
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

    default Market toMarket(MarketCreateRequestDto dto) {
        Market market = marketMapper.toEntity(dto);
        Address address = Address.builder()
                .roadAddress(dto.getRoadAddress())
                .jibunAddress(dto.getJibunAddress())
                .build();
        market.setAddress(address);
        return market;
    }

    void update(MarketUpdateRequestDto updateRequest, @MappingTarget Market market);

    default Market patchMarket(MarketUpdateRequestDto updateRequest, Market market) {
        marketMapper.update(updateRequest, market);
        Address address = Address.builder()
                .roadAddress(updateRequest.getRoadAddress())
                .jibunAddress(updateRequest.getJibunAddress())
                .regionCode(market.getAddress().getRegionCode())
                .latitude(market.getAddress().getLatitude())
                .longitude(market.getAddress().getLongitude())
                .build();
        market.setAddress(address);
        return market;
    }

    @Mapping(source = "id", target = "marketId")
    @Mapping(source = "market.address.roadAddress", target = "roadAddress")
    @Mapping(source = "market.address.jibunAddress", target = "jibunAddress")
    MarketResponseDto toMarketResponse(Market market);

    @Mapping(source = "market.id", target = "marketId")
    @Mapping(source = "retailer.id", target = "retailerId")
    ScheduleResponseDto toScheduleResponseDto(MarketSchedule marketSchedule);

    default List<ScheduleResponseDto> toScheduleResponseList(List<MarketSchedule> marketScheduleList) {
        return marketScheduleList.stream()
                .map(marketMapper::toScheduleResponseDto)
                .collect(Collectors.toList());
    }

    default List<MarketResponseDto> toMarketResponseList(List<Market> marketList) {
        return marketList.stream()
                .map(marketMapper::toMarketResponse)
                .collect(Collectors.toList());
    }



}
