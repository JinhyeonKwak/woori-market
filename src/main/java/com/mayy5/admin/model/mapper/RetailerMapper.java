package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.req.RetailerRequestDto;
import com.mayy5.admin.model.res.RetailerResponseDto;
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

    Retailer toEntity(RetailerRequestDto dto);

    void update(RetailerRequestDto retailerRequestDto, @MappingTarget Retailer retailer);

    RetailerResponseDto toDto(Retailer entity);
}
