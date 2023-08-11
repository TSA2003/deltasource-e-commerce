package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

public class CartItemTest {

    @BeforeAll
    public void setCurrentTime() {
        Server.setCurrentTime(LocalDate.of(2023, 8, 11));
    }

    @Test
    public void testCartItemCannotHaveNullProduct() {
        // Given
        Product product = null;
        int quantity = 1;
        AtomicReference<Product> product = new AtomicReference<Product>();

        // When
        assertThrows(IllegalArgumentException.class, () -> product.set(new Product(label, price)));

        // Then
        assertNull(product.get());
    }
}
