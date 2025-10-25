package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.BrandDTO;
import com.springboot.project.techno_shop.entity.Brand;
import com.springboot.project.techno_shop.service.BrandService;
import com.springboot.project.techno_shop.util.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("brands")
public class BrandController {

    @Autowired
    private BrandService brandService;

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody BrandDTO brandDTO){
        Brand brand = Mapper.toBrandDTO(brandDTO);
        brand = brandService.create(brand);
        return ResponseEntity.ok(brand);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Brand brand =  brandService.getById(id);
        return ResponseEntity.ok(brand);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody BrandDTO brandDTO){
        Brand brand = Mapper.toBrandDTO(brandDTO);
        brand = brandService.update(id, brand);
        return ResponseEntity.ok(brand);
    }
}
