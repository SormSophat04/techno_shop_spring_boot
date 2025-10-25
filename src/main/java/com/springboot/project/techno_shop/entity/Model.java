package com.springboot.project.techno_shop.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "model_tbl")
public class Model {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "model_name")
    private String name;

    @ManyToOne
    @JoinColumn(name = "brandId")
    private Brand brand;
}
