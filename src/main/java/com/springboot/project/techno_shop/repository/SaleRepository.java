package com.springboot.project.techno_shop.repository;

import com.springboot.project.techno_shop.dto.ProductSoldDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.Sale;
import com.springboot.project.techno_shop.projection.ProductSold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;

public interface SaleRepository extends JpaRepository<Sale,Long> {

    @Query(value = "select p.id as productId, p.product_name as productName, sum(sd.unit) as unit, sum(sd.unit * sd.amount) as totalAmount from sale_detail_tbl sd\n" +
            "inner join sale_tbl s on sd.sale_id=s.id\n" +
            "inner join product_tbl p on sd.product_id=p.id\n" +
            "where date(s.sale_date) >= :startDate and date(s.sale_date) <= :endDate\n" +
            "group by p.id,p.product_name", nativeQuery = true)
    List<ProductSold> findProductSold(LocalDate startDate, LocalDate endDate);
}
