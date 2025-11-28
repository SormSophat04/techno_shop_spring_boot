package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.ProductDTO;
import com.springboot.project.techno_shop.dto.ProductImportDTO;
import com.springboot.project.techno_shop.dto.SalePriceDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.ProductHistory;
import com.springboot.project.techno_shop.mapper.ProductMapper;
import com.springboot.project.techno_shop.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Map;


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

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Product> products = productService.getAll();
        return ResponseEntity.ok(products);
    }

    @GetMapping("/histories")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> getProductHistory(){
        List<ProductHistory> productHistories = productService.getProductHistoriesAll();
        return ResponseEntity.ok(productHistories);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Product product = productService.findById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/import")
    public ResponseEntity<?> importProduct(@RequestBody @Valid ProductImportDTO productImportDTO){
        productService.importProduct(productImportDTO);
        return ResponseEntity.ok("Import product successfully");
    }

    @PostMapping("{productId}/set-sale-price")
    public ResponseEntity<?> setSalePrice(@PathVariable Long productId, @RequestBody SalePriceDTO salePriceDTO){
        productService.setSalePrice(productId, salePriceDTO.getSalePrice());
        return ResponseEntity.ok("Set side price successfully");
    }

    @PostMapping("/upload-product")
    public ResponseEntity<?> uploadProduct(@RequestParam MultipartFile file) throws IOException {
        Map<Integer, String> mapError = productService.uploadProduct(file);
        return ResponseEntity.ok(mapError);
    }

}
