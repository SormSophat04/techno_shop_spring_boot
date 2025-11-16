package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.exception.NotFoundException;
import com.springboot.project.techno_shop.repository.ProductRepository;
import com.springboot.project.techno_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;

    @Override
    public Product create(Product product) {
        String name = "%s %s".formatted(product.getModel().getName(),product.getColor().getName());
        product.setName(name);
        return productRepository.save(product);
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found", id));
    }
}
