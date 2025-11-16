package com.springboot.project.techno_shop.repository;

import com.springboot.project.techno_shop.entity.Model;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface ModelRepository extends JpaRepository<Model, Long>, JpaSpecificationExecutor<Model> {

    List<Model> findByBrandId(Long brandId);

}
