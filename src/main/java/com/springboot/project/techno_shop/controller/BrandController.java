package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.BrandDTO;
import com.springboot.project.techno_shop.entity.Brand;
import com.springboot.project.techno_shop.mapper.BrandMapper;
import com.springboot.project.techno_shop.service.BrandService;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("brands")
public class BrandController {

    private final BrandService brandService;
    private final BrandMapper brandMapper;

    public BrandController(BrandService brandService, BrandMapper brandMapper) {
        this.brandService = brandService;
        this.brandMapper = brandMapper;
    }

    @RequestMapping(method = RequestMethod.POST)
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

//        List<BrandDTO> list = brandService.getBrands(params)
//                .stream()
//                .map(brandMapper::toBrandDTO)
//                .toList();

        return ResponseEntity.ok(pages.getContent());
    }

    //Update
    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody BrandDTO brandDTO){
        Brand brand = brandMapper.toBrand(brandDTO);
        brand = brandService.update(id, brand);
        return ResponseEntity.ok(brandMapper.toBrandDTO(brand));
    }





    // Get By Name
//    @GetMapping("filter")
//    public ResponseEntity<?> getBrands(@RequestParam("name") String name){
//        List<BrandDTO> list = brandService.getBrands(name)
//                .stream()
//                .map(brandMapper::toBrandDTO)
//                .toList();
//        return ResponseEntity.ok(list);
//    }
}
