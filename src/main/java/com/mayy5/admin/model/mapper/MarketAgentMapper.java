package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.req.MarketAgentRequestDto;
import com.mayy5.admin.model.req.MarketCreateRequestDto;
import com.mayy5.admin.model.res.MarketAgentResponseDto;
import org.mapstruct.*;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;


@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface MarketAgentMapper {

    MarketAgentMapper marketAgentMapper = Mappers.getMapper(MarketAgentMapper.class);

    @Mapping(source = "marketAgentMeta", target = "meta")
    MarketAgent marketCreateDTOtoEntity(MarketCreateRequestDto dto);

    MarketAgent toEntity(MarketAgentRequestDto dto);

    void update(MarketAgentRequestDto marketAgentRequestDto, @MappingTarget MarketAgent marketAgent);

    MarketAgentResponseDto toDto(MarketAgent entity);


}
