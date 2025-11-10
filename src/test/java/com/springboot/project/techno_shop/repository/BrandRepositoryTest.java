package com.springboot.project.techno_shop.repository;

import com.springboot.project.techno_shop.entity.Brand;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DataJpaTest
public class BrandRepositoryTest {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private BrandRepository brandRepository;

    @Test
    public void testSaveAndFindById() {
        Brand brand = new Brand();
        brand.setName("Test Brand");

        brand = entityManager.persistAndFlush(brand);

        Optional<Brand> foundBrand = brandRepository.findById(brand.getId());

        assertTrue(foundBrand.isPresent());
        assertEquals(brand.getName(), foundBrand.get().getName());
    }
}
