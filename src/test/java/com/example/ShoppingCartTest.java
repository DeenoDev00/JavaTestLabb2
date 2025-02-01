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

    @Test
    void removeItem_shouldRemoveItemFromCart() {
        cart.addItem("Apple", 10.0, 2);
        cart.addItem("Banana", 5.0, 3);

        cart.removeItem("Apple");

        assertThat(cart.getTotalPrice()).isEqualTo(15.0);
    }

    @Test
    void updateQuantity_shouldUpdateItemQuantity() {
        cart.addItem("Apple", 10.0, 2);

        cart.updateQuantity("Apple", 5);

        assertThat(cart.getTotalPrice()).isEqualTo(50.0);
    }

    @Test
    void getTotalPrice_shouldReturnCorrectTotalPrice() {
        cart.addItem("Apple", 10.0, 2); // 2 äpplen à 10.0 = 20.0
        cart.addItem("Banana", 5.0, 3); // 3 bananer à 5.0 = 15.0

        double totalPrice = cart.getTotalPrice();

        assertThat(totalPrice).isEqualTo(35.0); // 20.0 + 15.0
    }

    @Test
    void applyDiscount_shouldApplyDiscountToTotalPrice() {
        cart.addItem("Apple", 10.0, 2); // 2 äpplen à 10.0 = 20.0
        cart.addItem("Banana", 5.0, 3); // 3 bananer à 5.0 = 15.0

        cart.applyDiscount(0.1); // 10% rabatt

        assertThat(cart.getTotalPrice()).isEqualTo(31.5); // 35.0 * 0.9
    }


}
