package com.springboot.project.techno_shop.enums;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum Permission {
    BRAND_READ("brand:read"),
    BRAND_WRITE("brand:write"),
    MODEL_READ("model:read"),
    MODEL_WRITE("model:write"),
    COLOR_READ("color:read"),
    COLOR_WRITE("color:write"),
    PRODUCT_READ("product:read"),
    PRODUCT_WRITE("product:write"),
    SALE_READ("sale:read"),
    SALE_WRITE("sale:write"),
    REPORT_READ("report:read");

    private String description;
}
