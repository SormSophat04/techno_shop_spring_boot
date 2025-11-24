package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.dto.ColorDTO;
import com.springboot.project.techno_shop.entity.Color;

import java.util.List;

public interface ColorService {

    Color create(Color color);
    List<Color> getAll();
    Color findById(Long id);
    Color update(Long id, Color color);
    void delete(Long id);
}
