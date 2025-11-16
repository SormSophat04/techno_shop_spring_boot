package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.entity.Product;

public interface ProductService {

    Product create(Product product);

    Product findById(Long id);

}
