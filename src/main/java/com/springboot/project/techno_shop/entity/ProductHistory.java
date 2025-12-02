package com.springboot.project.techno_shop.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@Table(name = "product_historys")
public class ProductHistory extends AuditEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private  Integer id;

    @Column(name = "import_unit")
    private Integer importUnit;

    @Column(name = "unit_price")
    private BigDecimal unitPrice;

    @Column(name = "date_import")
    private LocalDateTime dateImport;

    @ManyToOne
    @JoinColumn(name = "productId")
    private Product product;
}
