package com.springboot.project.techno_shop.mapper;

import com.springboot.project.techno_shop.dto.BrandDTO;
import com.springboot.project.techno_shop.entity.Brand;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface BrandMapper {

    BrandDTO toBrandDTO(Brand brand);
    Brand toBrand(BrandDTO brandDTO);

    BrandMapper INSTANCE = Mappers.getMapper(BrandMapper.class);

}
