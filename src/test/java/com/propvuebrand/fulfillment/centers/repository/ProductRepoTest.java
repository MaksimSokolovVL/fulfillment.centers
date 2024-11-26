package com.propvuebrand.fulfillment.centers.repository;

import com.propvuebrand.fulfillment.centers.BaseIntegrationTest;
import com.propvuebrand.fulfillment.centers.domain.entity.Product;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;

import static com.propvuebrand.fulfillment.centers.Helper.PRODUCT_SELLABLE;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;


class ProductRepoTest extends BaseIntegrationTest {
    @Autowired
    private ProductRepo productRepo;

    @Test
    @Transactional
    void testSaveIndividual() {
        Product individual = productRepo.save(PRODUCT_SELLABLE.get());
        assertTrue(Objects.nonNull(individual.getId()));
    }

    @Test
    @Transactional
    void testFindIndividual() {
        Product actualIndividual = productRepo.save(PRODUCT_SELLABLE.get());
        Product expected = productRepo.findById(actualIndividual.getId()).orElse(null);
        assertEquals(expected, actualIndividual);
    }
}