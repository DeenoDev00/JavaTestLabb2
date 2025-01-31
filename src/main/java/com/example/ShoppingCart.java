package com.example;

import java.util.HashMap;
import java.util.Map;

public class ShoppingCart {
    private final Map<String, Double> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }

    public void addItem(String itemName, double itemPrice, int quantity) {
        items.put(itemName, itemPrice * quantity);
    }

    public double getTotalPrice() {
        return items.values().stream().mapToDouble(Double::doubleValue).sum();
    }
}
