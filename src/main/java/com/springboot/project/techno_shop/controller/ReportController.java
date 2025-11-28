package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.ExpenseReportDTO;
import com.springboot.project.techno_shop.dto.ProductReportDTO;
import com.springboot.project.techno_shop.projection.ProductSold;
import com.springboot.project.techno_shop.service.ReportService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("report")
public class ReportController {
    private final ReportService reportService;

    @GetMapping("{startDate}/{endDate}")
    public ResponseEntity<?> productSold(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        List<ProductSold> productSold = reportService.getProductSold(startDate, endDate);
        return  ResponseEntity.ok(productSold);
    }

    @GetMapping("v2/{startDate}/{endDate}")
    public ResponseEntity<?> productSoldV2(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        List<ProductReportDTO> productReport = reportService.getProductReport(startDate, endDate);
        return ResponseEntity.ok(productReport);
    }

    @GetMapping("expense/{startDate}/{endDate}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> expenseReport(@PathVariable LocalDate startDate, @PathVariable LocalDate endDate) {
        List<ExpenseReportDTO> expenseReportDTOS = reportService.getExpenseReport(startDate, endDate);
        return ResponseEntity.ok(expenseReportDTOS);
    }
}
