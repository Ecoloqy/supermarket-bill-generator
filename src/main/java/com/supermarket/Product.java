package com.supermarket;

public class Product {
    private final int barcode;
    private final String name;
    private final int quantity;

    public Product(int barcode, String name, int quantity) {
        this.barcode = barcode;
        this.name = name;
        this.quantity = quantity;
    }

    public int getBarcode() {
        return barcode;
    }

    public String getName() {
        return name;
    }

    public int getQuantity() {
        return quantity;
    }
}
