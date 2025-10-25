package com.springboot.project.techno_shop.util;

import com.springboot.project.techno_shop.dto.BrandDTO;
import com.springboot.project.techno_shop.entity.Brand;

public class Mapper {

    public static Brand toBrandDTO(BrandDTO dto){
        Brand brand = new Brand();
        brand.setId(dto.getId());
        brand.setName(dto.getName());
        return brand;
    }
}
