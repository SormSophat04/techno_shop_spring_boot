package com.springboot.project.techno_shop.dto;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ExpenseReportDTO {
    private Long productId;
    private String productName;
    private Integer unit;
    private BigDecimal totalAmount;
}
