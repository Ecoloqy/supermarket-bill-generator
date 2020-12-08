package com.supermarket;

import com.supermarket.background.FilesReader;
import com.supermarket.handlers.InvalidOperationException;
import com.supermarket.models.Product;
import com.supermarket.models.Promotion;

import java.math.BigDecimal;
import java.net.URISyntaxException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class Main {
    public static void main(String[] args) {
        String promotionsFilename = args.length == 2 ? args[0] : "products.csv";
        String receiptFilename = args.length == 2 ? args[0] : "receipt.csv";

        BillGenerator billGenerator = new BillGenerator();
        FilesReader filesReader = new FilesReader();

        try {
            Path promotionResourcePath = Paths.get(Objects.requireNonNull(Main.class.getClassLoader().getResource(promotionsFilename)).toURI());
            Path receiptResourcePath = Paths.get(Objects.requireNonNull(Main.class.getClassLoader().getResource(receiptFilename)).toURI());
            List<Promotion> promotions = filesReader.readPromotions(promotionResourcePath.toFile());
            List<Product> products = filesReader.readProducts(promotionResourcePath.toFile(), receiptResourcePath.toFile());
            BigDecimal result = billGenerator.generate(products, promotions);
            System.out.println("The total cost of purchases: " + result.toString() + " EUR");
        } catch (InvalidOperationException | URISyntaxException | NullPointerException e) {
            e.printStackTrace();
        }

    }
}
