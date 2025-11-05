package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.entity.Brand;
import com.springboot.project.techno_shop.exception.ApiException;
import com.springboot.project.techno_shop.exception.NotFoundException;
import com.springboot.project.techno_shop.repository.BrandRepository;
import com.springboot.project.techno_shop.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;

import java.net.http.HttpConnectTimeoutException;
import java.util.List;
import java.util.Optional;

@Service
public class BrandServiceImpl implements BrandService {

    @Autowired
    private BrandRepository brandRepository;

    @Override
    public Brand create(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Brand getById(Long id) {
        return brandRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("Brand", id));
    }

    @Override
    public Brand update(Long id, Brand updateBrand) {
         Brand brand = getById(id);
         brand.setName(updateBrand.getName());
        return brandRepository.save(brand);
    }

    @Override
    public List<Brand> getBrands() {
        return brandRepository.findAll();
    }

    @Override
    public List<Brand> getBrands(String name) {
        return brandRepository.findByNameContaining(name);
    }

}
