package com.supermarket.background;

import com.supermarket.Product;
import com.supermarket.Promotion;
import com.supermarket.handlers.InvalidOperationException;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

public class FilesReader {
    private final PromotionReader promotionReader;
    private final ReceiptReader receiptReader;

    public FilesReader() {
        this.promotionReader = new PromotionReader();
        this.receiptReader = new ReceiptReader(promotionReader);
    }

    public List<Promotion> readPromotions(File promotionsFile) throws InvalidOperationException {
        return promotionReader.readPromotions(promotionsFile);
    }

    public List<Product> readProducts(File promotionsFile, File productsFile) throws InvalidOperationException {
        return receiptReader.readReceipt(promotionsFile, productsFile);
    }
}

class PromotionReader {
    public List<Promotion> readPromotions(File promotionsFile) throws InvalidOperationException {
        if (promotionsFile == null) throw new InvalidOperationException("Promotions file don't exist!");

        List<Promotion> promotions = new ArrayList<>();
        Path pathToFile = Paths.get(promotionsFile.getAbsolutePath());
        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            int id = 0;
            while (line != null) {
                String[] attributes = line.split(",");
                Promotion promotion = createPromotion(id, attributes);
                promotions.add(promotion);
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new InvalidOperationException("File don't have next line!");
        }
        return promotions;
    }

    private Promotion createPromotion(int id, String[] attributes) throws InvalidOperationException {
        try {
            int barcode = Integer.parseInt(attributes[0]);
            String name = attributes[1];
            int quantity = Integer.parseInt(attributes[2]);
            double price = Double.parseDouble(attributes[3]);
            return new Promotion(id, barcode, name, quantity, price);
        } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
            throw new InvalidOperationException("Invalid file format!", e);
        }
    }
}

class ReceiptReader {
    private final PromotionReader promotionsReader;

    public ReceiptReader(PromotionReader promotionsReader) {
        this.promotionsReader = promotionsReader;
    }

    public List<Product> readReceipt(File promotionsFile, File productsFile) throws InvalidOperationException {
        if (promotionsFile == null || productsFile == null) throw new InvalidOperationException("Promotions or products file don't exist!");
        List<Promotion> promotions = promotionsReader.readPromotions(promotionsFile);

        Map<Integer, Integer> productsCounter = new HashMap<>();
        Path pathToFile = Paths.get(productsFile.getAbsolutePath());
        try (BufferedReader reader = Files.newBufferedReader(pathToFile, StandardCharsets.UTF_8)) {
            String line = reader.readLine();
            while (line != null) {
                int barcode = 0;
                try {
                    barcode = Integer.parseInt(line);
                } catch (ArrayIndexOutOfBoundsException | NumberFormatException e) {
                    throw new InvalidOperationException("Invalid file format!", e);
                }
                if (productsCounter.containsKey(barcode)) {
                    productsCounter.put(barcode, productsCounter.get(barcode) + 1);
                } else {
                    productsCounter.put(barcode, 1);
                }
                line = reader.readLine();
            }
        } catch (IOException e) {
            throw new InvalidOperationException("File don't have next line!");
        }

        List<Product> products = new ArrayList<>();
        for (Map.Entry<Integer, Integer> entry : productsCounter.entrySet()) {
            Optional<Promotion> optionalPromotion =  promotions.stream()
                    .filter(e -> e.getBarcode() == entry.getKey())
                    .findFirst();

            if (optionalPromotion.isEmpty()) throw new InvalidOperationException("Product is not in shop!");

            Promotion promotion = optionalPromotion.get();
            products.add(new Product(promotion.getBarcode(), promotion.getName(), entry.getValue()));
        }

        return products;
    }
}