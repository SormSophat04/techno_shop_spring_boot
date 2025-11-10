package com.springboot.project.techno_shop.spec;

import com.springboot.project.techno_shop.entity.Brand;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BrandSpecTest {

    @Mock
    private Root<Brand> root;

    @Mock
    private CriteriaQuery<?> query;

    @Mock
    private CriteriaBuilder criteriaBuilder;

    @Test
    public void testToPredicateWithName() {
        BrandFilter brandFilter = new BrandFilter();
        brandFilter.setName("Test");

        BrandSpec brandSpec = new BrandSpec(brandFilter);

        Path<String> namePath = mock(Path.class);
        when(root.<String>get("name")).thenReturn(namePath);
        when(criteriaBuilder.lower(namePath)).thenReturn(namePath);
        when(criteriaBuilder.like(namePath, "%test%")).thenReturn(mock(Predicate.class));

        brandSpec.toPredicate(root, query, criteriaBuilder);

        verify(criteriaBuilder).like(namePath, "%test%");
    }

    @Test
    public void testToPredicateWithId() {
        BrandFilter brandFilter = new BrandFilter();
        brandFilter.setId(1L);

        BrandSpec brandSpec = new BrandSpec(brandFilter);

        Path<Object> idPath = mock(Path.class);
        when(root.get("id")).thenReturn(idPath);
        when(idPath.in(1L)).thenReturn(mock(Predicate.class));

        brandSpec.toPredicate(root, query, criteriaBuilder);

        verify(idPath).in(1L);
    }
}
