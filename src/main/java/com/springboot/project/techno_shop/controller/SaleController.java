package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.SaleDTO;
import com.springboot.project.techno_shop.entity.Sale;
import com.springboot.project.techno_shop.entity.SaleDetail;
import com.springboot.project.techno_shop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/sales")
public class SaleController {
    private final SaleService saleService;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody SaleDTO saleDTO){
        saleService.sell(saleDTO);
        return ResponseEntity.ok("Create sale successfully");
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<SaleDetail> sales = saleService.getAll();
        return ResponseEntity.ok(sales);
    }

    public ResponseEntity<?> getById(@PathVariable Long id){
        Sale sale = saleService.getById(id);
        return ResponseEntity.ok(sale);
    }

    @PutMapping("{id}/cancel")
    public ResponseEntity<?> cancelSale(@PathVariable Long id){
        saleService.cancelSale(id);
        return ResponseEntity.ok("Cancel sale successfully");
    }
}
