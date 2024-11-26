package com.propvuebrand.fulfillment.centers.domain.converter;


import com.propvuebrand.fulfillment.centers.domain.ProductStatus;
import jakarta.persistence.AttributeConverter;
import jakarta.persistence.Converter;

import java.util.stream.Stream;

@Converter(autoApply = true)
public class ProductStatusConverter implements AttributeConverter<ProductStatus, String> {

    @Override
    public String convertToDatabaseColumn(ProductStatus status) {
        if (status == null) {
            return null;
        }
        return status.getStatusCode();
    }

    @Override
    public ProductStatus convertToEntityAttribute(String code) {
        if (code == null) {
            return null;
        }
        return Stream.of(ProductStatus.values())
                .filter(c -> c.getStatusCode().equalsIgnoreCase(code))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("ет такого статуса statusCode: " + code));
    }
}


