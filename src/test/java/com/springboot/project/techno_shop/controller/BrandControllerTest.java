package com.springboot.project.techno_shop.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.project.techno_shop.dto.BrandDTO;
import com.springboot.project.techno_shop.entity.Brand;
import com.springboot.project.techno_shop.mapper.BrandMapper;
import com.springboot.project.techno_shop.mapper.ModelMapper;
import com.springboot.project.techno_shop.service.BrandService;
import com.springboot.project.techno_shop.service.ModelService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(BrandController.class)
public class BrandControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BrandService brandService;

    @MockBean
    private BrandMapper brandMapper;

    @MockBean
    private ModelService modelService;

    @MockBean
    private ModelMapper modelMapper;

    @Autowired
    private ObjectMapper objectMapper;

    private Brand brand;
    private BrandDTO brandDTO;

    @BeforeEach
    void setUp() {
        brand = new Brand();
        brand.setId(1L);
        brand.setName("Test Brand");

        brandDTO = new BrandDTO();
        brandDTO.setId(1L);
        brandDTO.setName("Test Brand");
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testCreateBrand() throws Exception {
        when(brandService.create(any(Brand.class))).thenReturn(brand);
        when(brandMapper.toBrand(any(BrandDTO.class))).thenReturn(brand);
        when(brandMapper.toBrandDTO(any(Brand.class))).thenReturn(brandDTO);

        mockMvc.perform(post("/brands")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Brand"));
    }

    @Test
    @WithMockUser
    void testGetBrandById() throws Exception {
        when(brandService.getById(1L)).thenReturn(brand);
        when(brandMapper.toBrandDTO(any(Brand.class))).thenReturn(brandDTO);

        mockMvc.perform(get("/brands/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Brand"));
    }

    @Test
    @WithMockUser
    void testGetBrands() throws Exception {
        Map<String, String> params = new HashMap<>();
        Page<Brand> brandPage = new PageImpl<>(Collections.singletonList(brand));
        when(brandService.getBrands(params)).thenReturn(brandPage);

        mockMvc.perform(get("/brands"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].id").value(1L))
                .andExpect(jsonPath("$[0].name").value("Test Brand"));
    }

    @Test
    @WithMockUser(roles = "ADMIN")
    void testUpdateBrand() throws Exception {
        when(brandService.update(any(Long.class), any(Brand.class))).thenReturn(brand);
        when(brandMapper.toBrand(any(BrandDTO.class))).thenReturn(brand);
        when(brandMapper.toBrandDTO(any(Brand.class))).thenReturn(brandDTO);

        mockMvc.perform(put("/brands/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(brandDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1L))
                .andExpect(jsonPath("$.name").value("Test Brand"));
    }
}