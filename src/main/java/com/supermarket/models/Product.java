package com.supermarket.models;

import java.util.Objects;

public class Product {
    private final int barcode;
    private final String name;
    private int quantity;

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

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    /**
     * equals and hashCode only necessary for tests
     * I search for assertIterable without need to sort but I can't find it
     *   so I decide to sort lists by hashCode in tests
     * there is no need to sort objects in valid working BillGenerator itself
     **/

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return barcode == product.barcode && quantity == product.quantity && Objects.equals(name, product.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(barcode, name, quantity);
    }
}
