package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

public class CartItemTest {

    @Test
    public void testCartItemCannotHaveNullProduct() {
        // Given
        Product product = null;
        int quantity = 1;
        AtomicReference<CartItem> cartItem = new AtomicReference<CartItem>();

        // When
        assertThrows(IllegalArgumentException.class, () -> cartItem.set(new CartItem(product, quantity)));

        // Then
        assertNull(cartItem.get());
    }

    @Test
    public void testCartItemCannotHaveZeroQuantity() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        Product product = new Product(label, price);
        int quantity = 0;
        AtomicReference<CartItem> cartItem = new AtomicReference<CartItem>();

        // When
        assertThrows(IllegalArgumentException.class, () -> cartItem.set(new CartItem(product, quantity)));

        // Then
        assertNull(cartItem.get());
    }

    @Test
    public void testCartItemCannotHaveNegativeQuantity() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        Product product = new Product(label, price);
        int quantity = -1;
        AtomicReference<CartItem> cartItem = new AtomicReference<CartItem>();

        // When
        assertThrows(IllegalArgumentException.class, () -> cartItem.set(new CartItem(product, quantity)));

        // Then
        assertNull(cartItem.get());
    }

    @Test
    public void testCalculatePrice() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        Product product = new Product(label, price);
        int quantity = 2;
        CartItem cartItem = new CartItem(product, quantity);
        BigDecimal expectedPrice = BigDecimal.valueOf(240);

        // When
        BigDecimal calculatedPrice = cartItem.calculatePrice();

        // Then
        assertTrue(calculatedPrice.compareTo(expectedPrice) == 0);
    }

    @Test
    public void testCalculateDiscountedPrice() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        Product product = new Product(label, price);
        LocalDate discountStart = LocalDate.now().minusDays(10);
        LocalDate discountEnd = LocalDate.now().plusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        Discount discount = new Discount(discountStart, discountEnd, discountRate);
        product.setDiscount(discount);
        int quantity = 2;
        CartItem cartItem = new CartItem(product, quantity);
        BigDecimal expectedPrice = BigDecimal.valueOf(216);

        // When
        BigDecimal calculatedPrice = cartItem.calculateDiscountedPrice();

        // Then
        assertTrue(calculatedPrice.compareTo(expectedPrice) == 0);
    }
}
