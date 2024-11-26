package com.propvuebrand.fulfillment.centers.service;

import com.propvuebrand.fulfillment.centers.domain.ProductStatus;
import com.propvuebrand.fulfillment.centers.domain.dto.ProductDto;
import com.propvuebrand.fulfillment.centers.exception.ProductException;
import com.propvuebrand.fulfillment.centers.mapper.ProductMapper;
import com.propvuebrand.fulfillment.centers.repository.ProductRepo;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collections;
import java.util.List;

import static java.util.Objects.requireNonNull;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductService {
    private static final String PRODUCT_NULL = "ID продукта не может быть null";
    private static final String NO_PRODUCT_BY_ID = "Нет продукта по ID: %s";
    private static final String STATUS_CANNOT_BE_NULL = "Status cannot be null";
    private final ProductMapper productMapper;
    private final ProductRepo productRepo;

    @Transactional(readOnly = true)
    public ResponseEntity<ProductDto> getProduct(Long id) {
        requireNonNull(id, PRODUCT_NULL);

        var productDto = productRepo.findById(id)
                .map(productMapper::toDto)
                .orElseThrow(() -> new ProductException(String.format(NO_PRODUCT_BY_ID, id)));
        return ResponseEntity.ok(productDto);
    }

    @Transactional(readOnly = true)
    public ResponseEntity<List<ProductDto>> getAllProducts() {
        var products = productRepo.findAll()
                .stream()
                .map(productMapper::toDto)
                .toList();

        System.out.println(System.getProperty("file.encoding"));
        log.info("Получено {} продуктов", products.size());
        return ResponseEntity.ok(products);
    }

    /**
     * Создание продукта
     */
    @Transactional
    public ResponseEntity<ProductDto> createProduct(ProductDto productDto) {
        requireNonNull(productDto, "Product data cannot be null");

        var product = productMapper.toEntity(productDto);

        var savedProduct = productRepo.save(product);

        log.info("Создание продукта - ID: {}", savedProduct.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(productMapper.toDto(savedProduct));
    }

    /**
     * Обновление существующего продукта
     */
    @Transactional
    public ResponseEntity<ProductDto> updateProduct(Long id, ProductDto productDto) {
        requireNonNull(id, PRODUCT_NULL);
        requireNonNull(productDto, "Данные о продукте не могут быть равны null");

        var existingProduct = productRepo.findById(id)
                .orElseThrow(() -> new ProductException(String.format(NO_PRODUCT_BY_ID, id)));

        productMapper.updateEntity(productDto, existingProduct);
        var updatedProduct = productRepo.save(existingProduct);

        log.info("Обновление существующего продукта по ID: {}", updatedProduct.getId());
        return ResponseEntity.ok(productMapper.toDto(updatedProduct));
    }

    /**
     * Удаление продукта по ID
     */
    @Transactional
    public ResponseEntity<String> deleteProduct(Long id) {
        requireNonNull(id, PRODUCT_NULL);

        if (!productRepo.existsById(id)) {
            throw new ProductException(String.format(NO_PRODUCT_BY_ID, id));
        }

        productRepo.deleteById(id);
        log.info("Удаление продукта по ID: {}", id);
        return ResponseEntity.ok(String.format("Удален продукт по ID: %s", id));
    }

    /**
     * Фильтрация продуктов по статусу
     */
    @Transactional(readOnly = true)
    public ResponseEntity<List<ProductDto>> getProductsByStatus(ProductStatus status) {
        requireNonNull(status, STATUS_CANNOT_BE_NULL);

        var products = productRepo.getProductsByStatusIn(Collections.singletonList(status));
        log.info("Filtered {} products by status: {}", products.size(), status);

        List<ProductDto> productDtos = products.stream()
                .map(productMapper::toDto)
                .toList();
        return ResponseEntity.ok(productDtos);
    }

    /**
     * Сумма значений по статуту
     */
    @Transactional(readOnly = true)
    public ResponseEntity<String> getValuesByStatus(ProductStatus status) {
        requireNonNull(status, STATUS_CANNOT_BE_NULL);
        var sum = productRepo.getTotalValueByStatus(status);
        return ResponseEntity.ok(String.format("Сумма общей стоимости продуктов %s со статусом: %s", sum, status));
    }

    /**
     * Сумма значений для ffc
     */
    @Transactional(readOnly = true)
    public ResponseEntity<String> getSumValueForFulfillmentCenter(String fulfillmentCenter) {
        requireNonNull(fulfillmentCenter, "Fulfillment Center cannot be null");
        var sum = productRepo.getSumValueByFulfillmentCenter(fulfillmentCenter);
        return ResponseEntity.ok(String.format("Сумма общей стоимости продуктов %s для fulfillmentCenter: %s", sum, fulfillmentCenter));
    }
}
