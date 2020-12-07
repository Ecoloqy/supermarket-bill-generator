package com.supermarket.background;

import com.supermarket.Product;
import com.supermarket.Promotion;
import com.supermarket.handlers.InvalidOperationException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertIterableEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class FilesReaderTest {
    private final FilesReader filesReader = new FilesReader();
    private static List<Promotion> expectedPromotions;
    private static List<Product> expectedProducts;
    private static File promotionsFile;
    private static File productsFile;

    @BeforeEach
    public void beforeEach() {
        expectedPromotions = new ArrayList<>();
        expectedProducts = new ArrayList<>();
    }

    @Test
    public void when_validFileIsProvided_should_returnValidListOfPromotions() throws InvalidOperationException {
        try {
            promotionsFile = Paths.get(Objects.requireNonNull(FilesReaderTest.class.getClassLoader().getResource("products_test.csv")).toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        expectedPromotions = Arrays.asList(
                new Promotion(0, 1001, "Beer", 1, 1.20),
                new Promotion(1, 1001, "Beer", 2, 2.00),
                new Promotion(2, 1244, "Egg", 1, 0.20),
                new Promotion(3, 1244, "Egg", 10, 1.90),
                new Promotion(4, 1016, "Milk", 1, 2.20),
                new Promotion(5, 1016, "Milk", 2, 4.00)
        );

        List<Promotion> obtainedPromotions = filesReader.readPromotions(promotionsFile);

        expectedPromotions.sort(Comparator.comparing(Promotion::hashCode));
        obtainedPromotions.sort(Comparator.comparing(Promotion::hashCode));
        assertIterableEquals(expectedPromotions, obtainedPromotions);
    }

    @Test
    public void when_validFileIsProvided_should_returnValidListOfProducts() throws InvalidOperationException {
        try {
            promotionsFile = Paths.get(Objects.requireNonNull(FilesReaderTest.class.getClassLoader().getResource("products_test.csv")).toURI()).toFile();
            productsFile = Paths.get(Objects.requireNonNull(FilesReaderTest.class.getClassLoader().getResource("receipt_test.csv")).toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        expectedProducts = Arrays.asList(
                new Product(1001, "Beer", 3),
                new Product(1244, "Egg", 8),
                new Product(1016, "Milk", 3)
        );

        List<Product> obtainedProducts = filesReader.readProducts(promotionsFile, productsFile);

        expectedProducts.sort(Comparator.comparing(Product::hashCode));
        obtainedProducts.sort(Comparator.comparing(Product::hashCode));
        assertIterableEquals(expectedProducts, obtainedProducts);
    }

    @Test
    public void when_promotionsFileNotExist_should_returnInvalidOperationException() {
        Executable executable = () -> filesReader.readPromotions(null);

        assertThrows(InvalidOperationException.class, executable);
    }

    @Test
    public void when_promotionsFileIsInInvalidFormat_should_returnInvalidOperationException() {
        try {
            promotionsFile = Paths.get(Objects.requireNonNull(FilesReaderTest.class.getClassLoader().getResource("products_invalid_test.csv")).toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Executable executable = () -> filesReader.readPromotions(promotionsFile);

        assertThrows(InvalidOperationException.class, executable);
    }

    @Test
    public void when_productsFileNotExist_should_returnInvalidOperationException() {
        Executable executable = () -> filesReader.readProducts(promotionsFile, null);

        assertThrows(InvalidOperationException.class, executable);
    }

    @Test
    public void when_productsFileIsInInvalidFormat_should_returnInvalidOperationException() {
        try {
            promotionsFile = Paths.get(Objects.requireNonNull(FilesReaderTest.class.getClassLoader().getResource("products_test.csv")).toURI()).toFile();
            productsFile = Paths.get(Objects.requireNonNull(FilesReaderTest.class.getClassLoader().getResource("receipt_invalid_test.csv")).toURI()).toFile();
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        Executable executable = () -> filesReader.readProducts(promotionsFile, productsFile);

        assertThrows(InvalidOperationException.class, executable);
    }
}