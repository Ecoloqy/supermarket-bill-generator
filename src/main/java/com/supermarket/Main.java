package com.supermarket;

import com.supermarket.background.FilesReader;
import com.supermarket.handlers.InvalidOperationException;
import com.supermarket.models.Product;
import com.supermarket.models.Promotion;

import java.io.File;
import java.math.BigDecimal;
import java.net.URISyntaxException;
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
            List<Promotion> promotions = filesReader.readPromotions(getResourcePath(receiptFilename));
            List<Product> products = filesReader.readProducts(getResourcePath(promotionsFilename), getResourcePath(receiptFilename));
            BigDecimal result = billGenerator.generate(products, promotions);
            System.out.println("The total cost of purchases: " + result.toString() + " EUR");
        } catch (InvalidOperationException | URISyntaxException | NullPointerException e) {
            e.printStackTrace();
        }

    }

    private static File getResourcePath(String s) throws URISyntaxException {
        return Paths.get(Objects.requireNonNull(Main.class.getClassLoader().getResource(s)).toURI()).toFile();
    }
}
