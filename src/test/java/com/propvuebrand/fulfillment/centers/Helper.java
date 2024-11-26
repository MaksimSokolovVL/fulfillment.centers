package com.propvuebrand.fulfillment.centers;

import com.propvuebrand.fulfillment.centers.domain.ProductStatus;
import com.propvuebrand.fulfillment.centers.domain.entity.Product;

import java.math.BigDecimal;
import java.util.function.Supplier;

public class Helper {

    public static Supplier<Product> PRODUCT_SELLABLE = () -> {
        var ffc = new Product();
        ffc.setProduct("p10");
        ffc.setStatus(ProductStatus.SELLABLE);
        ffc.setFulfillmentCenter("fc5");
        ffc.setQty(22);
        ffc.setValue(BigDecimal.valueOf(200));

        return ffc;
    };
}
