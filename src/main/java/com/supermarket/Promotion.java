package com.supermarket;

import java.math.BigDecimal;
import java.util.Objects;

public class Promotion {
    private final int id;
    private final int barcode;
    private final String name;
    private final int quantity;
    private final BigDecimal price;

    public Promotion(int id, int barcode, String name, int quantity, double price) {
        this.id = id;
        this.barcode = barcode;
        this.name = name;
        this.quantity = quantity;
        this.price = new BigDecimal(Double.toString(price));
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

    public BigDecimal getPrice() {
        return price;
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
        Promotion promotion = (Promotion) o;
        return barcode == promotion.barcode && quantity == promotion.quantity && Objects.equals(name, promotion.name) && Objects.equals(price, promotion.price);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, barcode, name, quantity, price);
    }
}
