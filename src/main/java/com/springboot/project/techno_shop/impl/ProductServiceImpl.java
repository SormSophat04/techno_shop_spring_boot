package com.springboot.project.techno_shop.impl;

import com.springboot.project.techno_shop.dto.ProductImportDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.ProductHistory;
import com.springboot.project.techno_shop.exception.ApiException;
import com.springboot.project.techno_shop.exception.NotFoundException;
import com.springboot.project.techno_shop.mapper.ProductMapper;
import com.springboot.project.techno_shop.repository.ProductHistoryRepository;
import com.springboot.project.techno_shop.repository.ProductRepository;
import com.springboot.project.techno_shop.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {
    private final ProductRepository productRepository;
    private final ProductHistoryRepository productHistoryRepository;
    private final ProductMapper productMapper;

    @Override
    public Product create(Product product) {
        String name = "%s %s".formatted(product.getModel().getName(),product.getColor().getName());
        product.setName(name);
        return productRepository.save(product);
    }

    @Override
    public List<Product> getAll() {
        return productRepository.findAll();
    }

    @Override
    public Product findById(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new NotFoundException("Product not found", id));
    }

    @Override
    public void importProduct(ProductImportDTO productImportDTO) {
        //update product
        Product product = findById(productImportDTO.getProductId());
        product.setAvailableUnit(product.getAvailableUnit() + productImportDTO.getImportUnit());
        productRepository.save(product);

        //save product import history
        productHistoryRepository.save(productMapper.toProductHistory(productImportDTO, product));
    }

    @Override
    public void setSalePrice(Long productId, BigDecimal salePrice) {
        Product product =  findById(productId);
        product.setPrice(salePrice);
        productRepository.save(product);
    }

    @Override
    public void validateStock(Long productId, Integer numberOfUnit) {
        Product product = findById(productId);
        if (product.getAvailableUnit() < numberOfUnit){
            throw new NotFoundException("Not enough stock", productId);
        }
    }

    @Override
    public Product getByModelIdAndColorId(Long modelId, Long colorId) {
        return productRepository.findByModelIdAndColorId(modelId, colorId)
                .orElseThrow(()-> new ApiException(HttpStatus.BAD_REQUEST, "Product not found model id=%s color id=%s".formatted(modelId, colorId)));
    }

    @Override
    public Map<Integer, String> uploadProduct(MultipartFile file) throws IOException {
        Map<Integer, String> map = new HashMap<>();

        Workbook workbook = new XSSFWorkbook(file.getInputStream());
        Sheet sheet = workbook.getSheet("products");
        Iterator<Row> rowIterator = sheet.iterator();
        rowIterator.next();

        while (rowIterator.hasNext()){
            int rowNumber = 0;
            try {

            Row next = rowIterator.next();
            int cellIndex = 0;

            Cell cellNo = next.getCell(cellIndex++);
            rowNumber = (int) cellNo.getNumericCellValue();

            Cell cellModel = next.getCell(cellIndex++);
            Long modelId = (long) cellModel.getNumericCellValue();

            Cell cellColor = next.getCell(cellIndex++);
            Long colorId = (long) cellColor.getNumericCellValue();

            Cell cellPrice = next.getCell(cellIndex++);
            BigDecimal importPrice = BigDecimal.valueOf(cellPrice.getNumericCellValue());

            Cell cellAvailableUnit = next.getCell(cellIndex++);
            Integer importAvailableUnit = (int) cellAvailableUnit.getNumericCellValue();
            if (importAvailableUnit < 1){
                throw new ApiException(HttpStatus.BAD_REQUEST, "Import unit must be greater than 0");
            }

            Cell cellDate = next.getCell(cellIndex++);
            String importDate = cellDate.getStringCellValue();

            Product product = getByModelIdAndColorId(modelId, colorId);
            product.setAvailableUnit(product.getAvailableUnit() + importAvailableUnit);
            productRepository.save(product);

            ProductHistory productHistory = new ProductHistory();
            productHistory.setDateImport(LocalDateTime.parse(importDate));
            productHistory.setUnitPrice(importPrice);
            productHistory.setImportUnit(importAvailableUnit);
            productHistory.setProduct(product);
            productHistoryRepository.save(productHistory);

            }catch (ApiException e){
                map.put(rowNumber, e.getMessage());
            }
        }
        return map;
    }
}
