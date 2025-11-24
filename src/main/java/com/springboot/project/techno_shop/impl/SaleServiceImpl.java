package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.dto.ProductSoldDTO;
import com.springboot.project.techno_shop.dto.SaleDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.Sale;
import com.springboot.project.techno_shop.entity.SaleDetail;
import com.springboot.project.techno_shop.exception.ApiException;
import com.springboot.project.techno_shop.mapper.SaleMapper;
import com.springboot.project.techno_shop.repository.ProductRepository;
import com.springboot.project.techno_shop.repository.SaleDetailRepository;
import com.springboot.project.techno_shop.repository.SaleRepository;
import com.springboot.project.techno_shop.service.ProductService;
import com.springboot.project.techno_shop.service.SaleService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class SaleServiceImpl implements SaleService {
    private final ProductService productService;
    private final ProductRepository productRepository;
    private final SaleRepository saleRepository;
    private final SaleMapper saleMapper;
    private final SaleDetailRepository saleDetailRepository;

    @Override
    public void sell(SaleDTO saleDTO) {
        List<Long> productIds = saleDTO.getProductSoldDTO().stream().map(ProductSoldDTO::getProductId).toList();
        productIds.forEach(productService::findById);

        List<Product> products = productRepository.findAllById(productIds);
        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        saleDTO.getProductSoldDTO().forEach(ps -> {
            Product product = productMap.get(ps.getProductId());
            if (product.getAvailableUnit() < ps.getNumberOfUnit()){
                throw new ApiException(HttpStatus.BAD_REQUEST, "Product [%s] is not enough stock".formatted(product.getName()));
            }
        });

        Sale sale = new Sale();
        sale.setSaleDate(saleDTO.getSaleDate());
        saleRepository.save(sale);

        saleDTO.getProductSoldDTO().forEach(ps -> {
            Product product = productMap.get(ps.getProductId());
            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setAmount(product.getPrice());
            saleDetail.setProduct(product);
            saleDetail.setSale(sale);
            saleDetail.setUnit(ps.getNumberOfUnit());
            saleDetailRepository.save(saleDetail);

            // decrease product sold
            product.setAvailableUnit(product.getAvailableUnit() - ps.getNumberOfUnit());
            productRepository.save(product);
        });
    }

    @Override
    public List<SaleDetail> getAll() {
        return saleDetailRepository.findAll();
    }

    @Override
    public Sale getById(Long id) {
        return saleRepository.findById(id)
                .orElseThrow(() -> new ApiException(HttpStatus.BAD_REQUEST, "Sale not found"));
    }

    @Override
    public void cancelSale(Long id) {
        //update sales status
        Sale sale = getById(id);
        sale.setActive(false);
        saleRepository.save(sale);

        //update stock
        List<SaleDetail> saleDetails = saleDetailRepository.findBySaleId(sale.getId());

        List<Long> productIds = saleDetails.stream()
                .map(sd -> sd.getProduct().getId()).toList();

        List<Product> products = productRepository.findAllById(productIds);
        Map<Long, Product> productMap = products.stream().collect(Collectors.toMap(Product::getId, Function.identity()));

        saleDetails.forEach(sd -> {
            Product product = productMap.get(sd.getProduct().getId());
            product.setAvailableUnit(product.getAvailableUnit() + sd.getUnit());
            productRepository.save(product);
        });


    }


    private void save(SaleDTO saleDTO){
        Sale sale = new Sale();
        sale.setSaleDate(saleDTO.getSaleDate());
        saleRepository.save(sale);

        // sale detail
        saleDTO.getProductSoldDTO().forEach(ps -> {
            Product product = productService.findById(ps.getProductId());
            SaleDetail saleDetail = new SaleDetail();
            saleDetail.setAmount(product.getPrice());
        });
    }


    private void validate(SaleDTO saleDTO){
        saleDTO.getProductSoldDTO().forEach( ps -> {
            Product product = productService.findById(ps.getProductId());
            if (product.getAvailableUnit() < ps.getNumberOfUnit()){
                throw new ApiException(HttpStatus.BAD_REQUEST, "Product [%s] is not enough stock".formatted(product.getName()));
            }
        });
    }
}
