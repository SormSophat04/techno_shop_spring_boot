package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.dto.ColorDTO;
import com.springboot.project.techno_shop.entity.Color;
import com.springboot.project.techno_shop.exception.NotFoundException;
import com.springboot.project.techno_shop.mapper.ColorMapper;
import com.springboot.project.techno_shop.service.ColorService;
import com.springboot.project.techno_shop.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public Color create(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public List<Color> getAll() {
        return colorRepository.findAll();
    }

    @Override
    public Color findById(Long id) {
        return colorRepository.findById(id).orElseThrow(() -> new NotFoundException("Color not found", id));
    }

    @Override
    public Color update(Long id, Color color) {
        Color colors = findById(id);
        colors.setName(color.getName());
        return colorRepository.save(colors);
    }

    @Override
    public void delete(Long id) {
        Color color = findById(id);
        colorRepository.deleteById(color.getId());
    }
}
