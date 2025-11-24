package com.springboot.project.techno_shop.repository;

import com.springboot.project.techno_shop.entity.Sale;
import com.springboot.project.techno_shop.entity.SaleDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SaleDetailRepository extends JpaRepository<SaleDetail,Long> {
    List<SaleDetail> findBySaleId(Long id);
}
