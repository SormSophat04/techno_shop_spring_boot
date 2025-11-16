package com.springboot.project.techno_shop.repository;

import com.springboot.project.techno_shop.entity.Sale;
import com.springboot.project.techno_shop.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SaleDetailRepository extends JpaRepository<SaleDetail,Long> {
}
