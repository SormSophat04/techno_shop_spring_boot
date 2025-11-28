package com.springboot.project.techno_shop.spec;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ProductHistoryFilter {
    private LocalDate startDate;
    private LocalDate endDate;
}
