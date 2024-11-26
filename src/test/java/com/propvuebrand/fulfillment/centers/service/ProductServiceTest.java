package com.propvuebrand.fulfillment.centers.service;

import com.propvuebrand.fulfillment.centers.domain.dto.ProductDto;
import com.propvuebrand.fulfillment.centers.mapper.ProductMapper;
import com.propvuebrand.fulfillment.centers.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;

import static com.propvuebrand.fulfillment.centers.Helper.LIST_PRODUCT;
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
        when(productRepo.findAll()).thenReturn(LIST_PRODUCT.get());
        List<ProductDto> expectedProducts = service.getAllProducts().getBody();
        List<ProductDto> productDtos = LIST_PRODUCT.get().stream().map(productMapper::toDto).toList();
        assertEquals("ok", expectedProducts, productDtos);
    }

}