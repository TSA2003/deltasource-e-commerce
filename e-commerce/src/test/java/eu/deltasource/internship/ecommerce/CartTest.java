package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

public class CartTest {

    @Test
    public void testAddingNullCartItemToCartIsNotPossible() {
        // Given
        CartItem cartItem = null;
        Cart cart = new Cart();
        int expectedItems = 0;

        // When
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(cartItem));

        // Then
        assertEquals(expectedItems, cart.getCartItems().stream().count());
    }

    @Test
    public void testAddingExistingCartItemIsNotPossible() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        Product product = new Product(label, price);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(product, firstQuantity);
        CartItem secondCartItem = new CartItem(product, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        int expectedItems = 1;

        // When
        assertThrows(IllegalArgumentException.class, () -> cart.addItem(secondCartItem));

        // Then
        assertEquals(expectedItems, cart.getCartItems().stream().count());
    }

    @Test
    public void testUpdateCartItemIsNotPossibleWithNegativeIndex() {
        // Given
        String firstLabel = "Test";
        BigDecimal firstPrice = BigDecimal.valueOf(100);
        Product firstProduct = new Product(firstLabel, firstPrice);
        String secondLabel = "Test 2";
        BigDecimal secondPrice = BigDecimal.valueOf(100);
        Product secondProduct = new Product(secondLabel, secondPrice);
        int firstQuantity = 1;
        int secondQuantity = 2;
        CartItem firstCartItem = new CartItem(firstProduct, firstQuantity);
        CartItem secondCartItem = new CartItem(secondProduct, secondQuantity);
        Cart cart = new Cart();
        cart.addItem(firstCartItem);
        cart.addItem(secondCartItem);
        int indexOfUpdatedItem = -1;
        int newQuantity = 4;
        int expectedQuantity = secondQuantity;

        // When
        assertThrows(IllegalArgumentException.class, () -> cart.updateItem(indexOfUpdatedItem, newQuantity));

        // Then
        assertEquals(expectedQuantity, cart.getCartItems().get(indexOfUpdatedItem).getQuantity());
    }
}
