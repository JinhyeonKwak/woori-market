package com.mayy5.admin.model.mapper;

import com.mayy5.admin.model.domain.Retailer;
import com.mayy5.admin.model.req.RetailerRequestDto;
import com.mayy5.admin.model.res.RetailerResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Component
@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.IGNORE,
        unmappedSourcePolicy = ReportingPolicy.IGNORE)
public interface RetailerMapper {

    RetailerMapper retailerMapper =  Mappers.getMapper(RetailerMapper.class);

    Retailer toEntity(RetailerRequestDto dto);

    default List<Retailer> toEntities(List<RetailerRequestDto> dtoList) {
        List<Retailer> retailerList = dtoList.stream().
                map(this::toEntity)
                .collect(Collectors.toList());
        return retailerList;
    };


    void update(RetailerRequestDto retailerRequestDto, @MappingTarget Retailer retailer);

    RetailerResponseDto toDto(Retailer entity);

    default List<RetailerResponseDto> toDtoList(List<Retailer> retailerList) {
        List<RetailerResponseDto> retailerResponseDtoList = new ArrayList<>();
        for (Retailer retailer : retailerList) {
            retailerResponseDtoList.add(this.toDto(retailer));
        }
        return retailerResponseDtoList;
    }


}
