package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.dto.ProductImportDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.ProductHistory;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

public interface ProductService {
    Product create(Product product);
    List<Product> getAll();
    List<ProductHistory> getProductHistoriesAll();
    Product findById(Long id);
    Product getByModelIdAndColorId(Long modelId, Long colorId);
    void importProduct(ProductImportDTO productImportDTO);
    void setSalePrice(Long productId, BigDecimal salePrice);
    void validateStock(Long productId, Integer numberOfUnit);
    Map<Integer, String> uploadProduct(MultipartFile file) throws IOException;
}
