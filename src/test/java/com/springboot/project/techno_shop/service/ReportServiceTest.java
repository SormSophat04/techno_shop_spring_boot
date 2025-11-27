package com.springboot.project.techno_shop.service;

import com.springboot.project.techno_shop.dto.ExpenseReportDTO;
import com.springboot.project.techno_shop.dto.ProductReportDTO;
import com.springboot.project.techno_shop.entity.Product;
import com.springboot.project.techno_shop.entity.ProductHistory;
import com.springboot.project.techno_shop.entity.SaleDetail;
import com.springboot.project.techno_shop.impl.ReportServiceImpl;
import com.springboot.project.techno_shop.projection.ProductSold;
import com.springboot.project.techno_shop.repository.ProductHistoryRepository;
import com.springboot.project.techno_shop.repository.ProductRepository;
import com.springboot.project.techno_shop.repository.SaleDetailRepository;
import com.springboot.project.techno_shop.repository.SaleRepository;
import com.springboot.project.techno_shop.spec.ProductHistoryFilter;
import com.springboot.project.techno_shop.spec.ProductHistorySpec;
import com.springboot.project.techno_shop.spec.SaleDetailFilter;
import com.springboot.project.techno_shop.spec.SaleDetailSpec;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ReportServiceTest {

    @Mock
    private SaleRepository saleRepository;

    @Mock
    private SaleDetailRepository saleDetailRepository;

    @Mock
    private ProductRepository productRepository;

    @Mock
    private ProductHistoryRepository productHistoryRepository;

    @InjectMocks
    private ReportServiceImpl reportService;

    private LocalDate startDate;
    private LocalDate endDate;
    private Product product1;
    private Product product2;

    @BeforeEach
    void setUp() {
        startDate = LocalDate.of(2025, 1, 1);
        endDate = LocalDate.of(2025, 1, 31);

        product1 = new Product();
        product1.setId(1L);
        product1.setName("Laptop");

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Mouse");
    }

    @Test
    void testGetProductSold_WithValidDateRange() {
        // Arrange
        List<ProductSold> mockProductSoldList = new ArrayList<>();
        when(saleRepository.findProductSold(startDate, endDate)).thenReturn(mockProductSoldList);

        // Act
        List<ProductSold> result = reportService.getProductSold(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(mockProductSoldList, result);
        verify(saleRepository, times(1)).findProductSold(startDate, endDate);
    }

    @Test
    void testGetProductSold_ReturnsProductSoldList() {
        // Arrange
        List<ProductSold> mockProductSoldList = new ArrayList<>();
        when(saleRepository.findProductSold(startDate, endDate)).thenReturn(mockProductSoldList);

        // Act
        List<ProductSold> result = reportService.getProductSold(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertIsInstance(result, List.class);
        verify(saleRepository, times(1)).findProductSold(startDate, endDate);
    }

    @Test
    void testGetProductReport_WithValidDateRange() {
        // Arrange
        SaleDetail saleDetail1 = new SaleDetail();
        saleDetail1.setId(1L);
        saleDetail1.setProduct(product1);
        saleDetail1.setUnit(5);
        saleDetail1.setAmount(BigDecimal.valueOf(1000));

        SaleDetail saleDetail2 = new SaleDetail();
        saleDetail2.setId(2L);
        saleDetail2.setProduct(product1);
        saleDetail2.setUnit(3);
        saleDetail2.setAmount(BigDecimal.valueOf(1000));

        List<SaleDetail> saleDetails = List.of(saleDetail1, saleDetail2);
        List<Product> products = List.of(product1);

        when(saleDetailRepository.findAll(any(Specification.class))).thenReturn(saleDetails);
        when(productRepository.findAllById(List.of(1L))).thenReturn(products);

        // Act
        List<ProductReportDTO> result = reportService.getProductReport(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        ProductReportDTO reportDTO = result.get(0);
        assertEquals(1L, reportDTO.getProductId());
        assertEquals("Laptop", reportDTO.getProductName());
        assertEquals(8, reportDTO.getUnit()); // 5 + 3
        assertEquals(BigDecimal.valueOf(8000), reportDTO.getTotalAmount()); // (5 + 3) * 1000
    }

    @Test
    void testGetProductReport_WithMultipleProducts() {
        // Arrange
        SaleDetail saleDetail1 = new SaleDetail();
        saleDetail1.setId(1L);
        saleDetail1.setProduct(product1);
        saleDetail1.setUnit(5);
        saleDetail1.setAmount(BigDecimal.valueOf(1000));

        SaleDetail saleDetail2 = new SaleDetail();
        saleDetail2.setId(2L);
        saleDetail2.setProduct(product2);
        saleDetail2.setUnit(10);
        saleDetail2.setAmount(BigDecimal.valueOf(50));

        List<SaleDetail> saleDetails = List.of(saleDetail1, saleDetail2);
        List<Product> products = List.of(product1, product2);

        when(saleDetailRepository.findAll(any(Specification.class))).thenReturn(saleDetails);
        when(productRepository.findAllById(any(List.class))).thenReturn(products);

        // Act
        List<ProductReportDTO> result = reportService.getProductReport(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        ProductReportDTO dto1 = result.stream()
                .filter(dto -> dto.getProductId().equals(1L))
                .findFirst()
                .orElse(null);
        assertNotNull(dto1);
        assertEquals("Laptop", dto1.getProductName());
        assertEquals(Integer.valueOf(5), dto1.getUnit());

        ProductReportDTO dto2 = result.stream()
                .filter(dto -> dto.getProductId().equals(2L))
                .findFirst()
                .orElse(null);
        assertNotNull(dto2);
        assertEquals("Mouse", dto2.getProductName());
        assertEquals(Integer.valueOf(10), dto2.getUnit());
    }

    @Test
    void testGetProductReport_WithEmptySaleDetails() {
        // Arrange
        List<SaleDetail> saleDetails = Collections.emptyList();
        when(saleDetailRepository.findAll(any(Specification.class))).thenReturn(saleDetails);
        when(productRepository.findAllById(any(List.class))).thenReturn(Collections.emptyList());

        // Act
        List<ProductReportDTO> result = reportService.getProductReport(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetProductReport_CalculatesCorrectTotals() {
        // Arrange
        SaleDetail saleDetail1 = new SaleDetail();
        saleDetail1.setId(1L);
        saleDetail1.setProduct(product1);
        saleDetail1.setUnit(2);
        saleDetail1.setAmount(BigDecimal.valueOf(100.50));

        SaleDetail saleDetail2 = new SaleDetail();
        saleDetail2.setId(2L);
        saleDetail2.setProduct(product1);
        saleDetail2.setUnit(3);
        saleDetail2.setAmount(BigDecimal.valueOf(100.50));

        List<SaleDetail> saleDetails = List.of(saleDetail1, saleDetail2);
        List<Product> products = List.of(product1);

        when(saleDetailRepository.findAll(any(Specification.class))).thenReturn(saleDetails);
        when(productRepository.findAllById(List.of(1L))).thenReturn(products);

        // Act
        List<ProductReportDTO> result = reportService.getProductReport(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        ProductReportDTO reportDTO = result.get(0);
        assertEquals(Integer.valueOf(5), reportDTO.getUnit()); // 2 + 3
        // Total amount = (2 * 100.50) + (3 * 100.50) = 501
        assertEquals(BigDecimal.valueOf(501), reportDTO.getTotalAmount());
    }

    @Test
    void testGetExpenseReport_WithValidDateRange() {
        // Arrange
        ProductHistory history1 = new ProductHistory();
        history1.setId(1);
        history1.setProduct(product1);
        history1.setImportUnit(10);
        history1.setUnitPrice(BigDecimal.valueOf(500));

        List<ProductHistory> productHistories = List.of(history1);
        List<Product> products = List.of(product1);

        when(productHistoryRepository.findAll(any(Specification.class))).thenReturn(productHistories);
        when(productRepository.findAllById(any(List.class))).thenReturn(products);

        // Act
        List<ExpenseReportDTO> result = reportService.getExpenseReport(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        ExpenseReportDTO expenseDTO = result.get(0);
        assertEquals(1L, expenseDTO.getProductId());
        assertEquals("Laptop", expenseDTO.getProductName());
        assertEquals(Integer.valueOf(10), expenseDTO.getUnit());
        assertEquals(BigDecimal.valueOf(5000), expenseDTO.getTotalAmount()); // 10 * 500
    }

    @Test
    void testGetExpenseReport_WithMultipleProducts() {
        // Arrange
        ProductHistory history1 = new ProductHistory();
        history1.setId(1);
        history1.setProduct(product1);
        history1.setImportUnit(10);
        history1.setUnitPrice(BigDecimal.valueOf(500));

        ProductHistory history2 = new ProductHistory();
        history2.setId(2);
        history2.setProduct(product2);
        history2.setImportUnit(50);
        history2.setUnitPrice(BigDecimal.valueOf(20));

        List<ProductHistory> productHistories = List.of(history1, history2);
        List<Product> products = List.of(product1, product2);

        when(productHistoryRepository.findAll(any(Specification.class))).thenReturn(productHistories);
        when(productRepository.findAllById(any(List.class))).thenReturn(products);

        // Act
        List<ExpenseReportDTO> result = reportService.getExpenseReport(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(2, result.size());

        ExpenseReportDTO dto1 = result.stream()
                .filter(dto -> dto.getProductId().equals(1L))
                .findFirst()
                .orElse(null);
        assertNotNull(dto1);
        assertEquals("Laptop", dto1.getProductName());
        assertEquals(Integer.valueOf(10), dto1.getUnit());
        assertEquals(BigDecimal.valueOf(5000), dto1.getTotalAmount());

        ExpenseReportDTO dto2 = result.stream()
                .filter(dto -> dto.getProductId().equals(2L))
                .findFirst()
                .orElse(null);
        assertNotNull(dto2);
        assertEquals("Mouse", dto2.getProductName());
        assertEquals(Integer.valueOf(50), dto2.getUnit());
        assertEquals(BigDecimal.valueOf(1000), dto2.getTotalAmount());
    }

    @Test
    void testGetExpenseReport_WithMultipleHistoriesForSameProduct() {
        // Arrange
        ProductHistory history1 = new ProductHistory();
        history1.setId(1);
        history1.setProduct(product1);
        history1.setImportUnit(10);
        history1.setUnitPrice(BigDecimal.valueOf(500));

        ProductHistory history2 = new ProductHistory();
        history2.setId(2);
        history2.setProduct(product1);
        history2.setImportUnit(5);
        history2.setUnitPrice(BigDecimal.valueOf(500));

        List<ProductHistory> productHistories = List.of(history1, history2);
        List<Product> products = List.of(product1);

        when(productHistoryRepository.findAll(any(Specification.class))).thenReturn(productHistories);
        when(productRepository.findAllById(any(List.class))).thenReturn(products);

        // Act
        List<ExpenseReportDTO> result = reportService.getExpenseReport(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        ExpenseReportDTO expenseDTO = result.get(0);
        assertEquals(1L, expenseDTO.getProductId());
        assertEquals("Laptop", expenseDTO.getProductName());
        assertEquals(Integer.valueOf(15), expenseDTO.getUnit()); // 10 + 5
        assertEquals(BigDecimal.valueOf(7500), expenseDTO.getTotalAmount()); // (10 * 500) + (5 * 500)
    }

    @Test
    void testGetExpenseReport_WithEmptyProductHistories() {
        // Arrange
        List<ProductHistory> productHistories = Collections.emptyList();
        when(productHistoryRepository.findAll(any(Specification.class))).thenReturn(productHistories);
        when(productRepository.findAllById(any(List.class))).thenReturn(Collections.emptyList());

        // Act
        List<ExpenseReportDTO> result = reportService.getExpenseReport(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetExpenseReport_CalculatesCorrectTotals() {
        // Arrange
        ProductHistory history1 = new ProductHistory();
        history1.setId(1);
        history1.setProduct(product1);
        history1.setImportUnit(5);
        history1.setUnitPrice(BigDecimal.valueOf(150.75));

        ProductHistory history2 = new ProductHistory();
        history2.setId(2);
        history2.setProduct(product1);
        history2.setImportUnit(3);
        history2.setUnitPrice(BigDecimal.valueOf(150.75));

        List<ProductHistory> productHistories = List.of(history1, history2);
        List<Product> products = List.of(product1);

        when(productHistoryRepository.findAll(any(Specification.class))).thenReturn(productHistories);
        when(productRepository.findAllById(any(List.class))).thenReturn(products);

        // Act
        List<ExpenseReportDTO> result = reportService.getExpenseReport(startDate, endDate);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        ExpenseReportDTO expenseDTO = result.get(0);
        assertEquals(Integer.valueOf(8), expenseDTO.getUnit()); // 5 + 3
        // Total amount = (5 * 150.75) + (3 * 150.75) = 1206
        assertEquals(BigDecimal.valueOf(1206), expenseDTO.getTotalAmount());
    }

    private void assertIsInstance(Object obj, Class<?> clazz) {
        assertTrue(clazz.isInstance(obj), "Object is not an instance of " + clazz.getName());
    }
}

