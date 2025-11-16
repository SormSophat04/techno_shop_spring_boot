package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.dto.ModelDTO;
import com.springboot.project.techno_shop.entity.Model;

import java.util.List;

public interface ModelService {

    Model create(Model model);

    List<Model> getByBrand(Long brandId);

    Model findById(Long id);

}
