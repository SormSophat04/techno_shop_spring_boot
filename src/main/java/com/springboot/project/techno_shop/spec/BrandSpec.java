package com.springboot.project.techno_shop.spec;

import com.springboot.project.techno_shop.entity.Brand;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.Data;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

@Data
public class BrandSpec implements Specification<Brand> {

    private final BrandFilter brandFilter;

    List<Predicate> predicates = new ArrayList<>();

    @Override
    public Predicate toPredicate(Root<Brand> brandRoot, CriteriaQuery<?> query, CriteriaBuilder criteriaBuilder) {
        if (brandFilter.getName() != null){
            Predicate name = criteriaBuilder.like(criteriaBuilder
                                            .lower(brandRoot.get("name")), STR."%\{brandFilter.getName().toLowerCase()}%");
            predicates.add(name);
        }
        if (brandFilter.getId() != null){
            Predicate id = brandRoot.get("id").in(brandFilter.getId());
            predicates.add(id);
        }

        BrandSpec brandSpec = new BrandSpec(brandFilter);

        Pageable pageable = PageRequest.of(1, 10);

        return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
    }
}
