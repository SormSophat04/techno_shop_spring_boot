package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.dto.SaleDTO;
import com.springboot.project.techno_shop.entity.Sale;
import com.springboot.project.techno_shop.entity.SaleDetail;

import java.util.List;

public interface SaleService {
    void sell(SaleDTO saleDTO);
    List<SaleDetail> getAll();
    Sale getById(Long id);

    void cancelSale(Long id);
}
