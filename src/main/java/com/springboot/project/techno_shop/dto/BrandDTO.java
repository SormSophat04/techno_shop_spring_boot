package com.springboot.project.techno_shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class BrandDTO {
    @NotNull(message = "Brand name is required")
    private String name;

    public void setId(long l) {
    }
}
