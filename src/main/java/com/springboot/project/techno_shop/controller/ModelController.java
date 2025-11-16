package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.ModelDTO;
import com.springboot.project.techno_shop.entity.Model;
import com.springboot.project.techno_shop.mapper.ModelMapper;
import com.springboot.project.techno_shop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/models")
public class ModelController {

    private final ModelService modelService;
    private final ModelMapper modelMapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ModelDTO modelDTO) {
        Model model = modelMapper.toModel(modelDTO);
        model = modelService.create(model);
        return ResponseEntity.ok(modelMapper.toModelDTO(model));
    }
}
