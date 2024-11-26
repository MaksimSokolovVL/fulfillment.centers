package com.propvuebrand.fulfillment.centers;

import com.propvuebrand.fulfillment.centers.domain.ProductStatus;
import com.propvuebrand.fulfillment.centers.domain.entity.Product;

import java.math.BigDecimal;
import java.util.List;
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

    public static Supplier<List<Product>> LIST_PRODUCT = () -> {
        var ffc1 = new Product();
        ffc1.setId(1L);
        ffc1.setProduct("p10");
        ffc1.setStatus(ProductStatus.SELLABLE);
        ffc1.setFulfillmentCenter("fc5");
        ffc1.setQty(22);
        ffc1.setValue(BigDecimal.valueOf(200));

        var ffc2 = new Product();
        ffc2.setId(2L);
        ffc2.setProduct("p1");
        ffc2.setStatus(ProductStatus.INBOUND);
        ffc2.setFulfillmentCenter("fc15");
        ffc2.setQty(223);
        ffc2.setValue(BigDecimal.valueOf(300));

        return List.of(ffc1, ffc2);
    };

}
