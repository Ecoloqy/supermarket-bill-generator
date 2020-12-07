package com.supermarket;

import com.supermarket.background.FilesReader;
import com.supermarket.handlers.InvalidOperationException;

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

        FilesReader filesReader = new FilesReader();
        List<Promotion> promotions = null;
        List<Product> products = null;

        try {
            File promotionsFile = Paths.get(Objects.requireNonNull(Main.class.getClassLoader().getResource(promotionsFilename)).toURI()).toFile();
            File receiptFile = Paths.get(Objects.requireNonNull(Main.class.getClassLoader().getResource(receiptFilename)).toURI()).toFile();
            promotions = filesReader.readPromotions(promotionsFile);
            products = filesReader.readProducts(promotionsFile, receiptFile);
        } catch (InvalidOperationException | URISyntaxException e) {
            e.printStackTrace();
        }

        BillGenerator billGenerator = new BillGenerator();
        BigDecimal result = billGenerator.generate(products, promotions);
        System.out.println("The total cost of purchases: " + result.toString());
    }
}
