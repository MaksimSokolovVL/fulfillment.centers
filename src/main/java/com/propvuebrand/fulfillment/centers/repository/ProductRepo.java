package com.propvuebrand.fulfillment.centers.repository;

import com.propvuebrand.fulfillment.centers.domain.ProductStatus;
import com.propvuebrand.fulfillment.centers.domain.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.util.List;

public interface ProductRepo extends JpaRepository<Product, Long> {
    List<Product> getProductsByStatusIn(List<ProductStatus> statuses);

    @Query("SELECT COALESCE(SUM(p.value), 0) FROM Product p WHERE p.status = :status")
    Long getTotalValueByStatus(@Param("status") ProductStatus status);

    @Query("SELECT COALESCE(SUM(p.value), 0) FROM Product p WHERE p.fulfillmentCenter = :ffc")
    BigDecimal getSumValueByFulfillmentCenter(@Param("ffc") String fulfillmentCenter);
}
