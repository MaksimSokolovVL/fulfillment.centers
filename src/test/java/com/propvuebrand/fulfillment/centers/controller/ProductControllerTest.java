package com.propvuebrand.fulfillment.centers.controller;

import com.propvuebrand.fulfillment.centers.BaseIntegrationTest;
import com.propvuebrand.fulfillment.centers.domain.dto.ProductDto;
import com.propvuebrand.fulfillment.centers.repository.ProductRepo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.propvuebrand.fulfillment.centers.Helper.PRODUCT_SELLABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class ProductControllerTest extends BaseIntegrationTest {
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private ProductRepo productRepo;

    @Test
    void testGetIndividual_Success() {

        var individual = productRepo.save(PRODUCT_SELLABLE.get());
        Long id = individual.getId();

        ResponseEntity<ProductDto> response = restTemplate.getForEntity(
                "/product/{id}", ProductDto.class, id);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        ProductDto dto = response.getBody();
        System.out.println("######## " + dto); // todo
    }

    @Test
    void testGetIndividual_NotFound() {
        Long nonExistentId = 999L;

        ResponseEntity<String> response = restTemplate.getForEntity(
                "/product/{id}", String.class, nonExistentId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals("no individual by id: " + nonExistentId, response.getBody());
    }


}