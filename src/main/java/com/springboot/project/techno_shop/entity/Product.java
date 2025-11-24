package com.springboot.project.techno_shop.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import lombok.Data;

import java.math.BigDecimal;

@Data
@Entity
@Table(name = "product_tbl", uniqueConstraints = @UniqueConstraint(columnNames = {"modelId", "colorId"}))
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "product_name", unique = true)
    private String name;

    @Column(name = "available_unit")
    private Integer availableUnit;

    @DecimalMin(value = "0.001", message = "Price must be greater than 0")
    @Column(name = "price")
    private BigDecimal price;

    @Column(name = "image_path")
    private String imagePath;

    @ManyToOne
    @JoinColumn(name = "modelId")
    private Model model;

    @ManyToOne
    @JoinColumn(name = "colorId")
    private Color color;

}
