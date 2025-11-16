package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.dto.ModelDTO;
import com.springboot.project.techno_shop.entity.Model;
import com.springboot.project.techno_shop.exception.NotFoundException;
import com.springboot.project.techno_shop.mapper.ModelMapper;
import com.springboot.project.techno_shop.repository.ModelRepository;
import com.springboot.project.techno_shop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ModelServiceImpl implements ModelService {

    private final ModelRepository modelRepository;
    private final ModelMapper modelMapper;


    @Override
    public Model create(Model model) {
        return modelRepository.save(model);
    }

    @Override
    public List<Model> getByBrand(Long brandId) {
        return modelRepository.findByBrandId(brandId);
    }

    @Override
    public Model findById(Long id) {
        return modelRepository.findById(id).orElseThrow(() -> new NotFoundException("Model not found", id));
    }

}
