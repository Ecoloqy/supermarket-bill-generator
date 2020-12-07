package com.supermarket.background;

import com.supermarket.Product;
import com.supermarket.Promotion;
import com.supermarket.handlers.InvalidOperationException;

import java.io.File;
import java.util.List;

public class FilesReader {
    private final PromotionReader promotionReader;
    private final ReceiptReader receiptReader;

    public FilesReader(PromotionReader promotionReader, ReceiptReader receiptReader) {
        this.promotionReader = promotionReader;
        this.receiptReader = receiptReader;
    }

    public List<Promotion> readPromotions(File promotionsFile) throws InvalidOperationException {
        if (promotionsFile == null) throw new InvalidOperationException("Promotions file don't exist!");
        return null;
    }

    public List<Product> readProducts(File promotionsFile, File productsFile) throws InvalidOperationException {
        if (promotionsFile == null || productsFile == null) throw new InvalidOperationException("Promotions or products file don't exist!");
        return null;
    }
}
