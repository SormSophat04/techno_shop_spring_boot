package com.springboot.project.techno_shop.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class ProductImportDTO {
    @NotNull(message = "Product id is required")
    private Long productId;

    @Min(value = 1, message = "Import unit must be greater than 0")
    private Integer importUnit;

    @DecimalMin(value = "0.001", message = "Import price must be greater than 0")
    private BigDecimal importPrice;

    @NotNull(message = "Import date is required")
    private LocalDateTime importDate;
}
