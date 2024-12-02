package com.propvuebrand.fulfillment.centers.service;

import com.propvuebrand.fulfillment.centers.domain.dto.ProductDto;
import com.propvuebrand.fulfillment.centers.mapper.ProductMapper;
import com.propvuebrand.fulfillment.centers.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static com.propvuebrand.fulfillment.centers.Helper.PAGE_PRODUCT;
import static org.mockito.Mockito.when;
import static org.springframework.test.util.AssertionErrors.assertEquals;

@ExtendWith(SpringExtension.class)
class ProductServiceTest {
    @Mock
    private ProductRepo productRepo;
    @InjectMocks
    private ProductService service;
    @Mock
    private ProductMapper productMapper = ProductMapper.INSTANCE;

    @Test
    void getAllProducts() {
        PageRequest pagination = PageRequest.of(0, 10);

        when(productRepo.findAll(pagination)).thenReturn(PAGE_PRODUCT.get());
        Page<ProductDto> actualProducts = service.getAllProducts(0, 10).getBody();
        Page<ProductDto> extendsProduct = PAGE_PRODUCT.get().map(productMapper::toDto);
        assertEquals("ok", extendsProduct, actualProducts);
    }

    /**
     * Тесты тоже некоторые сложно читать, перепутаны expected с actual.
     * Например ProductServiceTest - в переменную expectedProducts на самом деле записываются продукты из вызова проверяемого сервиса.
     * */

}