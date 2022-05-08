package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.req.MarketAgentCreateRTO;
import com.mayy5.admin.model.req.MarketAgentUpdateRTO;
import com.mayy5.admin.model.res.MarketAgentRTO;
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
public interface MarketAgentMapper {

    MarketAgentMapper marketAgentMapper = Mappers.getMapper(MarketAgentMapper.class);

    @Mapping(source = "user", target = "user")
    @Mapping(source = "meta", target = "meta")
    MarketAgent toEntity(MarketAgentCreateRTO dto);

    @Mapping(source = "meta", target = "meta")
    void write(MarketAgentUpdateRTO dto, @MappingTarget MarketAgent marketAgent);

    @Mapping(source = "meta", target = "meta")
    @Mapping(source = "createAt", target = "createAt")
    @Mapping(source = "updateAt", target = "updateAt")
    MarketAgentRTO toDto(MarketAgent entity);

}
