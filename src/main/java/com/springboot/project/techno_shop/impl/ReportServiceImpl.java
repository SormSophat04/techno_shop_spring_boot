package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.dto.ExpenseReportDTO;
import com.springboot.project.techno_shop.dto.ProductReportDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.ProductHistory;
import com.springboot.project.techno_shop.entity.SaleDetail;
import com.springboot.project.techno_shop.projection.ProductSold;
import com.springboot.project.techno_shop.repository.ProductHistoryRepository;
import com.springboot.project.techno_shop.repository.ProductRepository;
import com.springboot.project.techno_shop.repository.SaleDetailRepository;
import com.springboot.project.techno_shop.repository.SaleRepository;
import com.springboot.project.techno_shop.service.ReportService;
import com.springboot.project.techno_shop.spec.ProductHistoryFilter;
import com.springboot.project.techno_shop.spec.ProductHistorySpec;
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
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportServiceImpl implements ReportService {
    private final SaleRepository saleRepository;
    private final SaleDetailRepository saleDetailRepository;
    private final ProductRepository productRepository;
    private final ProductHistoryRepository productHistoryRepository;
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

    @Override
    public List<ExpenseReportDTO> getExpenseReport(LocalDate startDate, LocalDate endDate) {
        ProductHistoryFilter productHistoryFilter = new ProductHistoryFilter();
        productHistoryFilter.setStartDate(startDate);
        productHistoryFilter.setEndDate(endDate);
        ProductHistorySpec spec = new ProductHistorySpec(productHistoryFilter);
        List<ProductHistory> productHistories = productHistoryRepository.findAll(spec);

        Set<Long> productId = productHistories.stream().map(ph -> ph.getProduct().getId())
                .collect(Collectors.toSet());
        List<Product> products = productRepository.findAllById(productId);
        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        Map<Product, List<ProductHistory>> historyMap = productHistories.stream()
                .collect(Collectors.groupingBy(ProductHistory::getProduct));

        var expenseReportDTOs = new ArrayList<ExpenseReportDTO>();

        for (var entry : historyMap.entrySet()){
            Product key = productMap.get(entry.getKey().getId());
            List<ProductHistory> importProduct = entry.getValue();

            int totalUnit = importProduct.stream().mapToInt(ProductHistory::getImportUnit).sum();

            double totalAmount = importProduct.stream()
                    .mapToDouble(imp -> imp.getImportUnit() * imp.getUnitPrice().doubleValue()).sum();

            var expenseReportDTO = new ExpenseReportDTO();
            expenseReportDTO.setProductId(key.getId());
            expenseReportDTO.setProductName(key.getName());
            expenseReportDTO.setUnit(totalUnit);
            expenseReportDTO.setTotalAmount(BigDecimal.valueOf(totalAmount));
            expenseReportDTOs.add(expenseReportDTO);
        }
        return expenseReportDTOs;
    }


}
