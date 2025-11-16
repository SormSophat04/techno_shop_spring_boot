package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.entity.Color;

public interface ColorService {

    Color create(Color color);

    Color findById(Long id);
}
