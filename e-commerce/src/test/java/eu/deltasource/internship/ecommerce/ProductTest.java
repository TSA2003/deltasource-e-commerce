package eu.deltasource.internship.ecommerce;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class ProductTest {

    @Test
    public void testProductCannotHaveEmptyLabel() {
        // Given
        String label = "";
        BigDecimal price = BigDecimal.ONE;
        AtomicReference<Product> product = new AtomicReference<Product>();

        // When
        assertThrows(IllegalArgumentException.class, () -> product.set(new Product(label, price)));

        // Then
        assertNull(product.get());
    }

    @Test
    public void testProductCannotHaveNegativePrice() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(-1);
        AtomicReference<Product> product = new AtomicReference<Product>();

        // When
        assertThrows(IllegalArgumentException.class, () -> product.set(new Product(label, price)));

        // Then
        assertNull(product.get());
    }
}
