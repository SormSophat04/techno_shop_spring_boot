package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.dto.ModelDTO;
import com.springboot.project.techno_shop.entity.Model;

import java.util.List;

public interface ModelService {

    Model create(Model model);

    List<Model> getByBrand(Long id);

    Model findById(Long id);

    List<Model> getAll();

    void destroy(Long id);

    Model update(Long id, ModelDTO modelDTO);
}
