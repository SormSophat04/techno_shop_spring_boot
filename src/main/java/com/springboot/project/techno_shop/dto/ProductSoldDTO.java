package com.springboot.project.techno_shop.dto;

import jakarta.validation.constraints.Min;
import lombok.Data;

@Data
public class ProductSoldDTO {

    private Long productId;

    @Min(value = 1, message = "Number of unit must be greater than 0")
    private Integer numberOfUnit;
}
