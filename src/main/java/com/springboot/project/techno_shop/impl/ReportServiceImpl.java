package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.dto.ProductReportDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.SaleDetail;
import com.springboot.project.techno_shop.projection.ProductSold;
import com.springboot.project.techno_shop.repository.ProductRepository;
import com.springboot.project.techno_shop.repository.SaleDetailRepository;
import com.springboot.project.techno_shop.repository.SaleRepository;
import com.springboot.project.techno_shop.service.ReportService;
import com.springboot.project.techno_shop.spec.SaleDetailFilter;
import com.springboot.project.techno_shop.spec.SaleDetailSpec;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final ProductRepository productRepository;
    @Override
    public List<ProductSold> getProductSold(LocalDate startDate, LocalDate endDate) {
        return saleRepository.findProductSold(startDate, endDate);
    }

    @Override
    public List<ProductReportDTO> getProductReport(LocalDate startDate, LocalDate endDate) {
        List<ProductReportDTO> list = new ArrayList<>();
        SaleDetailFilter saleDetailFilter = new SaleDetailFilter();
        saleDetailFilter.setStartDate(startDate);
        saleDetailFilter.setEndDate(endDate);
        Specification<SaleDetail> spec = new SaleDetailSpec(saleDetailFilter);
        List<SaleDetail> saleDetails = saleDetailRepository.findAll(spec);

        List<Long> productId = saleDetails.stream().map(sd -> sd.getProduct().getId()).toList();
        Map<Long, Product> productMap = productRepository.findAllById(productId)
                .stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        Map<Product, List<SaleDetail>> saldeDetailMap = saleDetails.stream().collect(Collectors.groupingBy(SaleDetail::getProduct));

        for (var entry : saldeDetailMap.entrySet()){
            Product product = productMap.get(entry.getKey().getId());
            List<SaleDetail> sdList = entry.getValue();

            //total unit
            Integer unit = sdList.stream().map(SaleDetail::getUnit).reduce(0, Integer::sum);
            //total amount
            double totalAmount = sdList.stream()
                    .mapToDouble(sd -> sd.getUnit() * sd.getAmount().doubleValue()).sum();

            ProductReportDTO reportDTO = new ProductReportDTO();
            reportDTO.setProductId(product.getId());
            reportDTO.setProductName(product.getName());
            reportDTO.setUnit(unit);
            reportDTO.setTotalAmount(BigDecimal.valueOf(totalAmount));
            list.add(reportDTO);
        }
        return list;
    }
}
