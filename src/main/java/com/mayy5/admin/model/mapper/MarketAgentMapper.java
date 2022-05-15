package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.req.MarketAgentRequestDto;
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

    @Mapping(source = "meta", target = "meta")
    MarketAgent toEntity(MarketAgentRequestDto dto);

    // marketAgentService의 updateMarketAgent() 메서드를 쓸 필요가 없다
    @Mapping(source = "meta", target = "meta")
    void update(MarketAgentRequestDto marketAgentRequestDto, @MappingTarget MarketAgent marketAgent);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "meta", target = "meta")
    @Mapping(source = "createAt", target = "createAt")
    @Mapping(source = "updateAt", target = "updateAt")
    MarketAgentResponseDto toDto(MarketAgent entity);


}
