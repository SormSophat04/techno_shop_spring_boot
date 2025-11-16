package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.BrandDTO;
import com.springboot.project.techno_shop.dto.ModelDTO;
import com.springboot.project.techno_shop.entity.Brand;
import com.springboot.project.techno_shop.entity.Model;
import com.springboot.project.techno_shop.mapper.BrandMapper;
import com.springboot.project.techno_shop.mapper.ModelMapper;
import com.springboot.project.techno_shop.service.BrandService;
import com.springboot.project.techno_shop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("brands")
public class BrandController {

    private final BrandService brandService;
    private final BrandMapper brandMapper;
    private final ModelService modelService;
    private final ModelMapper modelMapper;


    @PostMapping
    public ResponseEntity<?> create(@RequestBody BrandDTO brandDTO){
        Brand brand = brandMapper.toBrand(brandDTO);
        brand = brandService.create(brand);
        return ResponseEntity.ok(brandMapper.toBrandDTO(brand));
    }

    //Get By ID
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Brand brand =  brandService.getById(id);
        return ResponseEntity.ok(brandMapper.toBrandDTO(brand));
    }

    //Get All
    @GetMapping()
    public ResponseEntity<?> getBrands(@RequestParam Map<String, String> params){
        Page<Brand> pages = brandService.getBrands(params);

        return ResponseEntity.ok(pages.getContent());
    }

    //Update
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody BrandDTO brandDTO){
        Brand brand = brandMapper.toBrand(brandDTO);
        brand = brandService.update(id, brand);
        return ResponseEntity.ok(brandMapper.toBrandDTO(brand));
    }

    @GetMapping("{id}/models")
    public ResponseEntity<?> getModelByBrand(@PathVariable("id") Long brandId ){
        List<Model> brands = modelService.getByBrand(brandId);
        List<ModelDTO> list = brands.stream().map(modelMapper::toModelDTO).toList();
        return ResponseEntity.ok(list);
    }
}
