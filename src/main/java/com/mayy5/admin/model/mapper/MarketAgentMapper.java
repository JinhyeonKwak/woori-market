package com.mayy5.admin.model.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.req.MarketAgentRequestDto;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.res.MarketAgentResponseDto;

@Component
@Mapper(componentModel = "spring",
	unmappedTargetPolicy = ReportingPolicy.IGNORE,
	unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MarketAgentMapper {

	MarketAgentMapper marketAgentMapper = Mappers.getMapper(MarketAgentMapper.class);

	@Mapping(source = "marketAgent.meta", target = "meta")
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
