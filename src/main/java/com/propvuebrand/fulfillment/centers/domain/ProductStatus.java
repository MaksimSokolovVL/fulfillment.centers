package com.propvuebrand.fulfillment.centers.domain;

public enum ProductStatus {
    SELLABLE("Sellable"),
    UNFULFILLABLE("Unfulfillable"),
    INBOUND("Inbound");

    private final String statusCode;

    ProductStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }

    public static ProductStatus fromCode(String statusCode) {
        for (ProductStatus status : ProductStatus.values()) {
            if (status.statusCode.equalsIgnoreCase(statusCode)) {
                return status;
            }
        }
        throw new IllegalArgumentException("Нет такого статуса statusCode: " + statusCode);
    }
}
