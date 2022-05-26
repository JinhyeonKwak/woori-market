package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.req.MarketAgentRequestDto;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.res.MarketAgentResponseDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MarketAgentMapper {

    MarketAgentMapper marketAgentMapper = Mappers.getMapper(MarketAgentMapper.class);

    @Mapping(source = "marketAgentRequestDto.meta", target = "meta")
    MarketAgent toEntity(MarketCreateRequestDto dto);

    MarketAgent toEntity(MarketAgentRequestDto dto);

    void update(MarketAgentRequestDto marketAgentRequestDto, @MappingTarget MarketAgent marketAgent);

    MarketAgentResponseDto toDto(MarketAgent entity);

    default List<MarketAgentResponseDto> toDtoList(List<MarketAgent> marketAgentList) {
        List<MarketAgentResponseDto> marketAgentResponseDtoList = marketAgentList.stream()
                .map(this::toDto)
                .collect(Collectors.toList());
        return marketAgentResponseDtoList;
    }


}
