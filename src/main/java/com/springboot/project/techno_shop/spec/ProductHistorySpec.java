package com.springboot.project.techno_shop.spec;

import com.springboot.project.techno_shop.entity.ProductHistory;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Data
@AllArgsConstructor
public class ProductHistorySpec implements Specification<ProductHistory> {
    private ProductHistoryFilter productHistoryFilter;
    @Override
    public Predicate toPredicate(Root<ProductHistory> productHistory, CriteriaQuery<?> query, CriteriaBuilder cb) {
        List<Predicate> predicates = new ArrayList<>();

        if (Objects.nonNull(productHistoryFilter.getStartDate()) && Objects.nonNull(productHistoryFilter.getEndDate())){
            Predicate predicateDate = cb.between(productHistory.get("dateImport"), productHistoryFilter.getStartDate(), productHistoryFilter.getEndDate());
            predicates.add(predicateDate);
        }else
            if (Objects.nonNull(productHistoryFilter.getStartDate())){
            Predicate predicateStartDate = cb.greaterThanOrEqualTo(productHistory.get("dateImport"), productHistoryFilter.getStartDate());
            predicates.add(predicateStartDate);
        }else if (Objects.nonNull(productHistoryFilter.getEndDate())){
            Predicate predicateEndDate = cb.lessThanOrEqualTo(productHistory.get("dateImport"), productHistoryFilter.getEndDate());
            predicates.add(predicateEndDate);
        }

        return cb.and(predicates.toArray(Predicate[]::new));
    }
}
