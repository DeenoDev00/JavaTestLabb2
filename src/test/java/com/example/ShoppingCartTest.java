package com.example;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class ShoppingCartTest {

    private ShoppingCart cart;

    @BeforeEach
    void setUp() {
        cart = new ShoppingCart();
    }

    @Test
    void addItem_shouldAddItemToCart() {
        String itemName = "Apple";
        double itemPrice = 10.0;
        int quantity = 2;

        cart.addItem(itemName, itemPrice, quantity);


        assertThat(cart.getTotalPrice()).isEqualTo(20.0);
    }
}
