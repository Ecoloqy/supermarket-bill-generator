package com.supermarket;

import com.supermarket.handlers.InvalidOperationException;
import com.supermarket.models.Product;
import com.supermarket.models.Promotion;
import org.junit.Test;
import org.junit.jupiter.api.function.Executable;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BillGeneratorTest {

    private List<Product> products = Arrays.asList(
            new Product(1001, "Beer", 3),
            new Product(1244, "Egg", 8),
            new Product(1016, "Milk", 3)
    );

    private List<Promotion> promotions = Arrays.asList(
            new Promotion(0, 1001, "Beer", 1, 1.20),
            new Promotion(1, 1001, "Beer", 2, 2.00),
            new Promotion(2, 1244, "Egg", 1, 0.20),
            new Promotion(3, 1244, "Egg", 10, 1.90),
            new Promotion(4, 1016, "Milk", 1, 2.20),
            new Promotion(5, 1016, "Milk", 2, 4.00)
    );

    @Test
    public void when_generateWithValidValues_should_returnBigDecimalWithValidValue() throws InvalidOperationException {
        BillGenerator billGenerator = new BillGenerator();
        BigDecimal expectedResult = new BigDecimal("11.00");

        BigDecimal obtainedResult = billGenerator.generate(products, promotions);

        assertEquals(expectedResult, obtainedResult);
    }

    @Test
    public void when_generateWithNoPromotionsValues_should_throwIllegalArgumentException() {
        BillGenerator billGenerator = new BillGenerator();

        Executable executable = () -> billGenerator.generate(products, null);

        assertThrows(InvalidOperationException.class, executable);
    }

    @Test
    public void when_generateWithNoProductsValues_should_throwIllegalArgumentException() {
        BillGenerator billGenerator = new BillGenerator();

        Executable executable = () -> billGenerator.generate(null, promotions);

        assertThrows(InvalidOperationException.class, executable);
    }

    @Test
    public void when_generateWithEmptyProductsOrPromotions_should_throwIllegalArgumentException() {
        List<Product> emptyProducts = new ArrayList<>();
        List<Promotion> emptyPromotions = new ArrayList<>();
        BillGenerator billGenerator = new BillGenerator();

        Executable executable = () -> billGenerator.generate(emptyProducts, emptyPromotions);

        assertThrows(InvalidOperationException.class, executable);
    }
}
