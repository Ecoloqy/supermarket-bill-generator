package com.supermarket;

import java.math.BigDecimal;

public class Promotion {
    private final int id;
    private final int barcode;
    private final int quantity;
    private final BigDecimal price;

    public Promotion(int id, int barcode, int quantity, double price) {
        this.id = id;
        this.barcode = barcode;
        this.quantity = quantity;
        this.price = new BigDecimal(Double.toString(price));
    }

    public int getId() {
        return id;
    }

    public int getBarcode() {
        return barcode;
    }

    public int getQuantity() {
        return quantity;
    }

    public BigDecimal getPrice() {
        return price;
    }
}
