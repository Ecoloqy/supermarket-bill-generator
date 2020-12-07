package com.supermarket.background;

import com.supermarket.Product;
import com.supermarket.Promotion;

import java.io.File;
import java.util.List;

public class FilesReader {
    private final PromotionReader promotionReader;
    private final ReceiptReader receiptReader;

    public FilesReader(PromotionReader promotionReader, ReceiptReader receiptReader) {
        this.promotionReader = promotionReader;
        this.receiptReader = receiptReader;
    }

    public List<Promotion> readPromotions(File promotionsFile) {
        return null;
    }

    public List<Product> readProducts(File promotionsFile, File productsFile) {
        return null;
    }

    public PromotionReader getPromotionReader() {
        return promotionReader;
    }

    public ReceiptReader getReceiptReader() {
        return receiptReader;
    }
}
