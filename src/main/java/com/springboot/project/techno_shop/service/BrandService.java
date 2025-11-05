package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.entity.Brand;

import java.util.List;

public interface BrandService {
    Brand create(Brand brand);
    Brand getById(Long id);
    Brand update(Long id, Brand updateBrand);
    List<Brand> getBrands();
    List<Brand> getBrands(String name);
}
