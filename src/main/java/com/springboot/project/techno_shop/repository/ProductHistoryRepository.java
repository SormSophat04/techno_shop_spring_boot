package com.springboot.project.techno_shop.repository;

import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.ProductHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface ProductHistoryRepository extends JpaRepository<ProductHistory,Long>, JpaSpecificationExecutor<ProductHistory> {
}
