package com.springboot.project.techno_shop.mapper;

import com.springboot.project.techno_shop.dto.ProductDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.service.ColorService;
import com.springboot.project.techno_shop.service.ModelService;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {ModelService.class, ColorService.class})
public interface ProductMapper {

    @Mapping(target = "model", source = "modelId")
    @Mapping(target = "color", source = "colorId")
    Product toProduct(ProductDTO productDTO);

}
