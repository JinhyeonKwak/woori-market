package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.MarketAgent;
import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.req.MarketAgentRequest;
import com.mayy5.admin.model.req.RetailerRequest;
import com.mayy5.admin.model.res.MarketAgentResponse;
import com.mayy5.admin.model.res.RetailerResponse;
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
public interface RetailerMapper {

    RetailerMapper retailerMapper =  Mappers.getMapper(RetailerMapper.class);

    @Mapping(source = "meta", target = "meta")
    Retailer toEntity(RetailerRequest dto);

    @Mapping(source = "meta", target = "meta")
    void update(RetailerRequest retailerRequest, @MappingTarget Retailer retailer);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "meta", target = "meta")
    @Mapping(source = "createAt", target = "createAt")
    @Mapping(source = "updateAt", target = "updateAt")
    RetailerResponse toDto(Retailer entity);
}
