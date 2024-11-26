package com.propvuebrand.fulfillment.centers.mapper;

import com.propvuebrand.fulfillment.centers.BaseDataTest;
import com.propvuebrand.fulfillment.centers.domain.dto.ProductDto;
import com.propvuebrand.fulfillment.centers.domain.entity.Product;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ProductMapperTest extends BaseDataTest {
    @Test

    public void dto_to_entity_mapping_test() throws IOException {
        var productDto = readJson("input-product-dto.json", ProductDto.class);
        var product = ProductMapper.INSTANCE.toEntity(productDto);
        var input = OBJECT_MAPPER.writeValueAsString(product);
        var output = readTree("output-product.json").toString();
        assertEquals(input, output);
    }

    @Test
    public void entity_to_dto_mapping_test() throws IOException {
        var product = readJson("input-product.json", Product.class);
        var productDto = ProductMapper.INSTANCE.toDto(product);
        var input = OBJECT_MAPPER.writeValueAsString(productDto);
        var output = readTree("output-product-dto.json").toString();
        assertEquals(input, output);
    }

}