package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.entity.Brand;
import com.springboot.project.techno_shop.exception.NotFoundException;
import com.springboot.project.techno_shop.repository.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class BrandServiceImplTest {

    @Mock
    private BrandRepository brandRepository;

    @InjectMocks
    private BrandServiceImpl brandService;

    private Brand brand;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1L);
        brand.setName("Test Brand");
    }

    @Test
    void testCreateBrand() {
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);
        Brand createdBrand = brandService.create(new Brand());
        assertEquals(brand.getName(), createdBrand.getName());
    }

    @Test
    void testGetBrandById() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        Brand foundBrand = brandService.getById(1L);
        assertEquals(brand.getName(), foundBrand.getName());
    }

    @Test
    void testGetBrandById_NotFound() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());
        assertThrows(NotFoundException.class, () -> brandService.getById(1L));
    }

    @Test
    void testUpdateBrand() {
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));
        when(brandRepository.save(any(Brand.class))).thenReturn(brand);

        Brand updatedBrand = new Brand();
        updatedBrand.setName("Updated Brand");

        Brand result = brandService.update(1L, updatedBrand);
        assertEquals("Updated Brand", result.getName());
    }

    @Test
    void testGetBrands() {
        Map<String, String> params = new HashMap<>();
        Page<Brand> brandPage = new PageImpl<>(Collections.singletonList(brand));
        when(brandRepository.findAll(any(Specification.class), any(Pageable.class))).thenReturn(brandPage);

        Page<Brand> result = brandService.getBrands(params);
        assertEquals(1, result.getTotalElements());
        assertEquals(brand.getName(), result.getContent().get(0).getName());
    }
}
