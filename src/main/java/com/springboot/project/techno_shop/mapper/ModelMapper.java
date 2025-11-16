package com.springboot.project.techno_shop.mapper;

import com.springboot.project.techno_shop.dto.ModelDTO;
import com.springboot.project.techno_shop.entity.Model;
import com.springboot.project.techno_shop.service.BrandService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", uses = {BrandService.class})
public interface ModelMapper {

    ModelMapper INSTANCE = Mappers.getMapper(ModelMapper.class);

    @Mapping(target = "brand", source = "brandId")
    Model toModel(ModelDTO dto);

    @Mapping(target = "brandId", source = "brand.id")
    ModelDTO toModelDTO(Model model);

}
