package com.propvuebrand.fulfillment.centers.domain.dto;

import com.propvuebrand.fulfillment.centers.domain.ProductStatus;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductDto {
    private String product;
    private ProductStatus status;
    private String fulfillmentCenter;
    private Integer qty;
    private BigDecimal value;
}
