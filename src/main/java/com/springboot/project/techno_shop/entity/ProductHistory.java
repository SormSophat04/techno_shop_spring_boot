package com.springboot.project.techno_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Entity
@Table(name = "product_history_tbl")
public class ProductHistory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "import_unit")
    private Integer importUnit;

    @Column(name = "date_import")
    private LocalDate dateImport;

    @Column(name = "export_unit")
    private Integer exportUnit;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
