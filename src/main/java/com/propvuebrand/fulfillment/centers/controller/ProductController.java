package com.propvuebrand.fulfillment.centers.controller;

import com.propvuebrand.fulfillment.centers.controller.api.ProductControllerOpenApi;
import com.propvuebrand.fulfillment.centers.domain.ProductStatus;
import com.propvuebrand.fulfillment.centers.domain.dto.ProductDto;
import com.propvuebrand.fulfillment.centers.exception.ProductException;
import com.propvuebrand.fulfillment.centers.service.ProductService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/product")
@RequiredArgsConstructor
public class ProductController implements ProductControllerOpenApi {
    private final ProductService service;

    @Override
    public ResponseEntity<ProductDto> getProduct(Long id) {
        return service.getProduct(id);
    }

    @Override
    public ResponseEntity<Page<ProductDto>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return service.getAllProducts(page, size);
    }

    @Override
    public ResponseEntity<ProductDto> postCreateProduct(@RequestBody ProductDto productDto) {
        return service.createProduct(productDto);
    }

    @Override
    public ResponseEntity<ProductDto> putUpdateProduct(Long id, @RequestBody ProductDto productDto) {
        return service.updateProduct(id, productDto);
    }

    @Override
    public ResponseEntity<String> deleteProduct(@PathVariable("id") Long id) {
        return service.deleteProduct(id);
    }

    @Override
    @GetMapping("/filter")
    public ResponseEntity<List<ProductDto>> getFilterProductsByStatus(@RequestParam("status") ProductStatus status) {
        return service.getProductsByStatus(status);
    }

    @Override
    public ResponseEntity<String> getSumProductsByStatus(@RequestParam("status") ProductStatus status) {
        return service.getValuesByStatus(status);
    }

    @Override
    public ResponseEntity<String> getSumValueByFulfillmentCenter(@RequestParam("status") String fulfillmentCenter) {
        return service.getSumValueForFulfillmentCenter(fulfillmentCenter);
    }

    /**
     * Обработка исключения, если продукт не найден
     */
    @ExceptionHandler(ProductException.class)
    public ResponseEntity<String> handleNoProductException(ProductException ex) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(ex.getMessage());
    }
}
