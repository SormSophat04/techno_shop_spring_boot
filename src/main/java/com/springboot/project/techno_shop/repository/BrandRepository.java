package com.springboot.project.techno_shop.repository;

import com.springboot.project.techno_shop.entity.Brand;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BrandRepository extends JpaRepository<Brand, Long> {

    List<Brand> findByNameContaining(String name);

}
