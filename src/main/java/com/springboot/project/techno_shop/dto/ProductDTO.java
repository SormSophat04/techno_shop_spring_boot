package com.springboot.project.techno_shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ProductDTO {

    @NotNull(message = "Model id is required")
    private Long modelId;

    @NotNull(message = "Color id is required")
    private Long colorId;
}
