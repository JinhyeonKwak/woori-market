package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.req.MarketAgentRequestDto;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.res.MarketAgentResponseDto;
import org.mapstruct.Mapper;
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
public interface MarketAgentMapper {

    MarketAgentMapper marketAgentMapper = Mappers.getMapper(MarketAgentMapper.class);

    default MarketAgent toEntity(MarketCreateRequestDto dto) {
        return toEntity(dto.getMarketAgent());
    }

    MarketAgent toEntity(MarketAgentRequestDto dto);

    void update(MarketAgentRequestDto marketAgentRequestDto, @MappingTarget MarketAgent marketAgent);

    MarketAgentResponseDto toDto(MarketAgent entity);

    default List<MarketAgentResponseDto> toDtoList(List<MarketAgent> marketAgentList) {
        List<MarketAgentResponseDto> marketAgentResponseDtoList = marketAgentList.stream()
                .map(marketAgentMapper::toDto)
                .collect(Collectors.toList());
        return marketAgentResponseDtoList;
    }


}
