package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class ProductTest {

    @BeforeAll
    public void setCurrentTime() {
        Server.setCurrentTime(LocalDate.of(2023, 8, 11));
    }

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

    @Test
    public void testProductPriceIsGetCorrectly() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal expectedPrice = BigDecimal.valueOf(120);
        Product product = new Product(label, price);
        int equal = 0;

        // When
        BigDecimal actualPrice = product.getPrice();

        // Then
        assertTrue(actualPrice.compareTo(expectedPrice) == equal);
    }

    @Test
    public void testProductDiscountEndDateCannotBeBeforeStartDate() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.of(2023, 8, 19);
        LocalDate discountEnd = LocalDate.of(2023, 8, 10);
        BigDecimal discount = BigDecimal.valueOf(0.1);
        Product product = new Product(label, price);

        // When
        assertThrows(IllegalArgumentException.class, () -> product.createNewDiscount(discountStart, discountEnd, discount));

        // Then
        assertFalse(product.isDiscounted());
    }

    @Test
    public void testProductDiscountCannotBeOutsideOfRange0And1() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.of(2023, 8, 10);
        LocalDate discountEnd = LocalDate.of(2023, 8, 19);
        BigDecimal discountOne = BigDecimal.valueOf(-0.1);
        BigDecimal discountTwo = BigDecimal.valueOf(1.2);
        int equal = 0;
        BigDecimal expectedPrice = BigDecimal.ZERO;
        Product product = new Product(label, price);

        // When
        assertThrows(IllegalArgumentException.class, () -> product.createNewDiscount(discountStart, discountEnd, discountOne));
        assertThrows(IllegalArgumentException.class, () -> product.createNewDiscount(discountStart, discountEnd, discountTwo));

        // Then
        assertTrue(product.getDiscount().compareTo(expectedPrice) == equal);
    }

    @Test
    public void testProductIsNotOnDiscountBeforeGivenDiscountPeriod() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.of(2023, 8, 12);
        LocalDate discountEnd = LocalDate.of(2023, 8, 19);
        BigDecimal discount = BigDecimal.valueOf(0.1);
        Product product = new Product(label, price);

        // When
        product.createNewDiscount(discountStart, discountEnd, discount);

        // Then
        assertFalse(product.isDiscounted());
    }

    @Test
    public void testProductIsOnDiscountInGivenDiscountPeriod() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.of(2023, 8, 10);
        LocalDate discountEnd = LocalDate.of(2023, 8, 19);
        BigDecimal discount = BigDecimal.valueOf(0.1);
        Product product = new Product(label, price);

        // When
        product.createNewDiscount(discountStart, discountEnd, discount);

        // Then
        assertTrue(product.isDiscounted());
    }

    @Test
    public void testProductIsNotOnDiscountAfterGivenDiscountPeriod() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.of(2023, 8, 1);
        LocalDate discountEnd = LocalDate.of(2023, 8, 9);
        BigDecimal discount = BigDecimal.valueOf(0.1);
        Product product = new Product(label, price);

        // When
        product.createNewDiscount(discountStart, discountEnd, discount);

        // Then
        assertFalse(product.isDiscounted());
    }

    @Test
    public void testGetPriceWhileProductIsDiscounted() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.of(2023, 8, 1);
        LocalDate discountEnd = LocalDate.of(2023, 8, 12);
        BigDecimal discount = BigDecimal.valueOf(0.1);
        BigDecimal expectedPrice = BigDecimal.valueOf(108);
        int equal = 0;
        Product product = new Product(label, price);

        // When
        product.createNewDiscount(discountStart, discountEnd, discount);

        // Then
        assertTrue(product.getPrice().compareTo(expectedPrice) == equal);
    }

    @Test
    public void testLoyaltyDiscountIsReturnedCorrectly() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.of(2023, 8, 1);
        LocalDate discountEnd = LocalDate.of(2023, 8, 9);
        BigDecimal discount = BigDecimal.valueOf(0.1);
        BigDecimal expectedPrice = BigDecimal.valueOf(6);
        int equal = 0;
        Product product = new Product(label, price);

        // When
        product.setLoyaltyDiscount(true);

        // Then
        assertTrue(product.getLoyaltyDiscount().compareTo(expectedPrice) == equal);
    }

    @Test
    public void testLoyaltyDiscountIsZeroForDiscountedItems() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.of(2023, 8, 1);
        LocalDate discountEnd = LocalDate.of(2023, 8, 9);
        BigDecimal discount = BigDecimal.valueOf(0.1);
        BigDecimal expectedPrice = BigDecimal.valueOf(6);
        int equal = 0;
        Product product = new Product(label, price);
        product.createNewDiscount(discountStart, discountEnd, discount);

        // When
        product.setLoyaltyDiscount(true);

        // Then
        assertTrue(product.getLoyaltyDiscount().compareTo(expectedPrice) == equal);
    }
}
