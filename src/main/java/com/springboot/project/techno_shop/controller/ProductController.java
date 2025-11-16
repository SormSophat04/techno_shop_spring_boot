package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.ProductDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.mapper.ProductMapper;
import com.springboot.project.techno_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;
    private final ProductMapper productMapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ProductDTO productDTO){
        Product product = productMapper.toProduct(productDTO);
        product = productService.create(product);
        return ResponseEntity.ok(product);
    }
}
