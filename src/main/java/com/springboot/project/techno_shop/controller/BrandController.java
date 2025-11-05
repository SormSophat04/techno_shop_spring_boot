package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.BrandDTO;
import com.springboot.project.techno_shop.entity.Brand;
import com.springboot.project.techno_shop.mapper.BrandMapper;
import com.springboot.project.techno_shop.service.BrandService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("brands")
public class BrandController {

    private final BrandService brandService;

    public BrandController(BrandService brandService) {
        this.brandService = brandService;
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<?> create(@RequestBody BrandDTO brandDTO){
        Brand brand = BrandMapper.INSTANCE.toBrand(brandDTO);
        brand = brandService.create(brand);
        return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
    }

    //Get By ID
    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Brand brand =  brandService.getById(id);
        return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
    }

    //Get All
    @GetMapping()
    public ResponseEntity<?> getBrands(){
        List<BrandDTO> list = brandService.getBrands()
                .stream()
                .map(BrandMapper.INSTANCE::toBrandDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    // Get By Name
    @GetMapping("filter")
    public ResponseEntity<?> getBrands(@RequestParam("name") String name){
        List<BrandDTO> list = brandService.getBrands(name)
                .stream()
                .map(BrandMapper.INSTANCE::toBrandDTO)
                .toList();
        return ResponseEntity.ok(list);
    }

    //Update
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody BrandDTO brandDTO){
        Brand brand = BrandMapper.INSTANCE.toBrand(brandDTO);
        brand = brandService.update(id, brand);
        return ResponseEntity.ok(BrandMapper.INSTANCE.toBrandDTO(brand));
    }
}
