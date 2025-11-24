package com.springboot.project.techno_shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
public class SaleDTO {

    @NotNull
    private List<ProductSoldDTO> productSoldDTO = new ArrayList<>();

    private LocalDateTime saleDate;
}
