package com.example;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<String, Double> items;
    private final Map<String, Integer> quantities;

    public ShoppingCart() {
        this.items = new HashMap<>();
        this.quantities = new HashMap<>();
    }

    public double getTotalPrice() {
        return items.values().stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }


    public void addItem(String itemName, double itemPrice, int quantity) {
        if (quantity <= 0) {
            throw new IllegalArgumentException("Kvantitet måste vara större än 0");
        }
        items.put(itemName, itemPrice * quantity);
        quantities.put(itemName, quantity);
    }

    public void removeItem(String itemName) {
        items.remove(itemName);
        quantities.remove(itemName);
    }

    public void updateQuantity(String itemName, int newQuantity) {
        if (newQuantity <= 0) {
            throw new IllegalArgumentException("Kvantitet måste vara större än 0");
        }
        if (!items.containsKey(itemName)) {
            throw new IllegalArgumentException("Varan finns inte i varukorgen");
        }


        double totalPriceForItem = items.get(itemName);
        int currentQuantity = quantities.get(itemName);
        double pricePerUnit = totalPriceForItem / currentQuantity;

        items.put(itemName, pricePerUnit * newQuantity);
        quantities.put(itemName, newQuantity);
    }

    public void applyDiscount(double discountRate) {
        if (discountRate < 0 || discountRate > 1) {
            throw new IllegalArgumentException("Rabatt måste vara mellan 0 och 1");
        }
        for (Map.Entry<String, Double> entry : items.entrySet()) {
            double discountedPrice = entry.getValue() * (1 - discountRate);
            items.put(entry.getKey(), discountedPrice);
        }
    }





}
