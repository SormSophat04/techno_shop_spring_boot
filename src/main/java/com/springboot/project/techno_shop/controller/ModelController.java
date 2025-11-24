package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.ModelDTO;
import com.springboot.project.techno_shop.entity.Model;
import com.springboot.project.techno_shop.mapper.ModelMapper;
import com.springboot.project.techno_shop.service.ModelService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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

    @GetMapping
    public ResponseEntity<?> getAll() {
        List<Model> models = modelService.getAll();
        return ResponseEntity.ok(models);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable("id") Long id){
        Model model = modelService.findById(id);
        return ResponseEntity.ok(model);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable("id") Long id, @RequestBody ModelDTO modelDTO) {
        Model model = modelService.update(id, modelDTO);
        return ResponseEntity.ok(model);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable("id") Long id){
        modelService.destroy(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
