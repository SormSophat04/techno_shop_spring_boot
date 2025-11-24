package com.springboot.project.techno_shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;


@Data
public class ModelDTO {

    @NotNull(message = "Brand id is required")
    private Long brandId;

    @NotNull(message = "Model name is required")
    private String name;
}
