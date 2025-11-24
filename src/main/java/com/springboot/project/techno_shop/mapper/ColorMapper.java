package com.springboot.project.techno_shop.mapper;

import com.springboot.project.techno_shop.dto.ColorDTO;
import com.springboot.project.techno_shop.entity.Color;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface ColorMapper {

    ColorDTO toColorDTO(Color color);
    Color toColor(ColorDTO colorDTO);
}
