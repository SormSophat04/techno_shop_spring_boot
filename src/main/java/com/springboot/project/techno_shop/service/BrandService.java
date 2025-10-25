package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.entity.Brand;

public interface BrandService {
    Brand create(Brand brand);
    Brand getById(Long id);
    Brand update(Long id, Brand updateBrand);
}
