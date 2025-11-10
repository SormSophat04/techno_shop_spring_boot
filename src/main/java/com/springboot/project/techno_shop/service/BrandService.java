package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.entity.Brand;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Map;

public interface BrandService {
    Brand create(Brand brand);
    Brand getById(Long id);
    Brand update(Long id, Brand updateBrand);
//    List<Brand> getBrands(Map<String, String> params);
    Page<Brand> getBrands(Map<String, String> params);
}
