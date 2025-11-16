package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.entity.Color;
import com.springboot.project.techno_shop.exception.NotFoundException;
import com.springboot.project.techno_shop.service.ColorService;
import com.springboot.project.techno_shop.repository.ColorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ColorServiceImpl implements ColorService {
    private final ColorRepository colorRepository;

    @Override
    public Color create(Color color) {
        return colorRepository.save(color);
    }

    @Override
    public Color findById(Long id) {
        return colorRepository.findById(id).orElseThrow(() -> new NotFoundException("Color not found", id));
    }
}
