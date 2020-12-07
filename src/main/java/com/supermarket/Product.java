package com.supermarket;

import java.math.BigDecimal;

public class Product {
    private final int barcode;
    private final String name;
    private final int quantity;
    private final BigDecimal price;

    public Product(int barcode, String name, int quantity, double price) {
        this.barcode = barcode;
        this.name = name;
        this.quantity = quantity;
        this.price = new BigDecimal(Double.toString(price));
    }
}
