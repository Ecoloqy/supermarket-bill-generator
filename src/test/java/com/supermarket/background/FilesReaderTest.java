package com.supermarket.background;

import com.supermarket.Product;
import com.supermarket.Promotion;
import com.supermarket.handlers.InvalidOperationException;
import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.function.Executable;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

public class FilesReaderTest {
    private static PromotionReader promotionReader;
    private static ReceiptReader receiptReader;
    private static List<Promotion> expectedPromotions;
    private static List<Product> expectedProducts;
    private static File promotionsFile;
    private static File productsFile;

    @BeforeAll
    public static void init() {
        promotionReader = new PromotionReader();
        receiptReader = new ReceiptReader();
        try {
            promotionsFile = Paths.get(Objects.requireNonNull(FilesReaderTest.class.getClassLoader().getResource("products_test.csv")).toURI()).toFile();
            productsFile = Paths.get(Objects.requireNonNull(FilesReaderTest.class.getClassLoader().getResource("receipt_test.csv")).toURI()).toFile();
        } catch (URISyntaxException e) {
            promotionsFile = null;
            productsFile = null;
        }
        System.out.println("A");
        System.out.println(FilesReaderTest.class);
    }

    @BeforeEach
    public void beforeEach() {
        expectedPromotions = new ArrayList<>();
        expectedProducts = new ArrayList<>();
    }

    @Test
    public void when_validFileIsProvided_should_returnValidListOfPromotions() {
        FilesReader filesReader = new FilesReader(promotionReader, receiptReader);
        expectedPromotions = Arrays.asList(
                new Promotion(0, 1001, 1, 1.20),
                new Promotion(1, 1001, 2, 2.00),
                new Promotion(2, 1244, 1, 0.20),
                new Promotion(3, 1244, 10, 1.90),
                new Promotion(4, 1016, 1, 2.20),
                new Promotion(5, 1016, 2, 4.00)
        );

        filesReader.readPromotions(promotionsFile);
    }

    @Test
    public void when_validFileIsProvided_should_returnValidListOfProducts() {
        FilesReader filesReader = new FilesReader(promotionReader, receiptReader);
        expectedProducts = Arrays.asList(
                new Product(1001, "Beer", 3),
                new Product(1244, "Egg", 8),
                new Product(1016, "Milk", 3)
        );

        filesReader.readProducts(promotionsFile, productsFile);
    }

    @Test
    public void when_promotionsFileNotExist_should_returnIOException() {
        FilesReader filesReader = new FilesReader(promotionReader, receiptReader);

        Executable executable = () -> filesReader.readPromotions(null);

        assertThrows(InvalidOperationException.class, executable);
    }

    @Test
    public void when_productsFileNotExist_should_returnIOException() {
        FilesReader filesReader = new FilesReader(promotionReader, receiptReader);

        Executable executable = () -> filesReader.readProducts(promotionsFile, null);

        assertThrows(InvalidOperationException.class, executable);
    }
}