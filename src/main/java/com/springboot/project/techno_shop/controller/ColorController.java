package com.springboot.project.techno_shop.controller;

import com.springboot.project.techno_shop.dto.ColorDTO;
import com.springboot.project.techno_shop.entity.Color;
import com.springboot.project.techno_shop.mapper.ColorMapper;
import com.springboot.project.techno_shop.service.ColorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/colors")
public class ColorController {
    private final ColorService colorService;
    private final ColorMapper colorMapper;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody ColorDTO colorDTO){
        Color color = colorMapper.toColor(colorDTO);
        color = colorService.create(color);
        return ResponseEntity.ok(colorMapper.toColorDTO(color));
    }

    @GetMapping
    public ResponseEntity<?> getAll(){
        List<Color> colors = colorService.getAll();
        return ResponseEntity.ok(colors);
    }

    @GetMapping("{id}")
    public ResponseEntity<?> getById(@PathVariable Long id){
        Color color = colorService.findById(id);
        return ResponseEntity.ok(color);
    }

    @PutMapping("{id}")
    public ResponseEntity<?> update(@PathVariable Long id, @RequestBody ColorDTO colorDTO){
        Color color = colorMapper.toColor(colorDTO);
        color = colorService.update(id, color);
        return ResponseEntity.ok(colorMapper.toColorDTO(color));
    }

    @DeleteMapping("{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        colorService.delete(id);
        return ResponseEntity.ok("Delete successfully");
    }
}
