package com.springboot.project.techno_shop.repository;

import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.Sale;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleRepository extends JpaRepository<Sale,Long> {
}
