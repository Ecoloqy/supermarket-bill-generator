package com.supermarket;

import com.supermarket.handlers.InvalidOperationException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class BillGenerator {
    public BigDecimal generate(List<Product> products, List<Promotion> promotions) throws InvalidOperationException {
        if (products == null || promotions == null) throw new InvalidOperationException("Can't generate result because one of the list not exist!");
        if (products.size() == 0 || promotions.size() == 0) throw new InvalidOperationException("Can't generate result because one of the list is empty!");

        BigDecimal result = BigDecimal.ZERO;
        for (Product product : products) {
            List<Promotion> promotionsOfThisProduct = promotions.stream()
                    .filter(e -> e.getBarcode() == product.getBarcode())
                    .sorted(Comparator.comparing(Promotion::getQuantity).reversed())
                    .collect(Collectors.toList());

            result = updateResultForOneProduct(result, product, promotionsOfThisProduct);
        }
        return result.setScale(2, RoundingMode.CEILING);
    }

    private BigDecimal updateResultForOneProduct(BigDecimal result, Product product, List<Promotion> promotionsOfThisProduct) throws InvalidOperationException {
        while (product.getQuantity() != 0) {
            Promotion promotion = getBestPromotionForProduct(product, promotionsOfThisProduct);
            if (product.getQuantity() < 0) throw new InvalidOperationException("Promotion start from " + promotion.getQuantity() + " " + product.getName());
            int oldQuantity = product.getQuantity();
            product.setQuantity(oldQuantity - promotion.getQuantity());
            result = result.add(promotion.getPrice());
        }
        return result;
    }

    private Promotion getBestPromotionForProduct(Product product, List<Promotion> promotionsOfThisProduct) throws InvalidOperationException {
        for (Promotion promotion : promotionsOfThisProduct) {
            if (promotion.getQuantity() <= product.getQuantity()) {
                return promotion;
            }
        }
        throw new InvalidOperationException("There is no promotion for this product!");
    }
}
