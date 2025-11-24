package com.springboot.project.techno_shop.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ColorDTO {
    @NotNull(message = "Color name is required")
    private String name;
}
