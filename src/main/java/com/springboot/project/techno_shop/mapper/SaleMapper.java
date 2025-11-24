package com.springboot.project.techno_shop.mapper;

import com.springboot.project.techno_shop.dto.SaleDTO;
import com.springboot.project.techno_shop.entity.Sale;
import org.mapstruct.Mapper;
import org.springframework.stereotype.Component;

@Mapper(componentModel = "spring")
@Component
public interface SaleMapper {
    Sale toSale(SaleDTO saleDTO);
    SaleDTO toSaleDTO(Sale sale);
}
