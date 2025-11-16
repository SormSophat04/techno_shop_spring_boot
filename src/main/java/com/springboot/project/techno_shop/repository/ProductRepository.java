package com.springboot.project.techno_shop.repository;

import com.springboot.project.techno_shop.entity.Color;
import com.springboot.project.techno_shop.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductRepository extends JpaRepository<Product,Long>, JpaSpecificationExecutor<Product> {
}
