package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
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
    public void testProductIsNotOnDiscountBeforeGivenDiscountPeriod() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.now().plusDays(10);
        LocalDate discountEnd = LocalDate.now().plusDays(20);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        Discount discount = new Discount(discountStart, discountEnd, discountRate);
        Product product = new Product(label, price);

        // When
        product.setDiscount(discount);

        // Then
        assertFalse(product.isDiscounted());
    }

    @Test
    public void testProductIsOnDiscountInGivenDiscountPeriod() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.now().minusDays(10);
        LocalDate discountEnd = LocalDate.now().plusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        Discount discount = new Discount(discountStart, discountEnd, discountRate);
        Product product = new Product(label, price);

        // When
        product.setDiscount(discount);

        // Then
        assertTrue(product.isDiscounted());
    }

    @Test
    public void testProductIsNotOnDiscountAfterGivenDiscountPeriod() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.now().minusDays(10);
        LocalDate discountEnd = LocalDate.now().minusDays(5);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        Discount discount = new Discount(discountStart, discountEnd, discountRate);
        Product product = new Product(label, price);

        // When
        product.setDiscount(discount);

        // Then
        assertFalse(product.isDiscounted());
    }

    @Test
    public void testGetDiscountedPriceWhileProductIsDiscounted() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.now().minusDays(10);
        LocalDate discountEnd = LocalDate.now().plusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        Discount discount = new Discount(discountStart, discountEnd, discountRate);
        BigDecimal expectedPrice = BigDecimal.valueOf(108);
        Product product = new Product(label, price);

        // When
        product.setDiscount(discount);

        // Then
        assertTrue(product.getDiscountedPrice().compareTo(expectedPrice) == 0);
    }

    @Test
    public void testLoyaltyDiscountPriceReductionIsReturnedCorrectly() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        BigDecimal loyaltyDiscountRate = BigDecimal.valueOf(0.05);
        LoyaltyDiscount loyaltyDiscount = new LoyaltyDiscount(loyaltyDiscountRate);
        BigDecimal expectedPrice = BigDecimal.valueOf(6);
        Product product = new Product(label, price);

        // When
        product.setLoyaltyDiscount(loyaltyDiscount);

        // Then
        assertTrue(product.getLoyaltyDiscountPriceReduction().compareTo(expectedPrice) == 0);
    }

    @Test
    public void testLoyaltyDiscountPriceReductionIsZeroForDiscountedItems() {
        // Given
        String label = "Test";
        BigDecimal price = BigDecimal.valueOf(100);
        LocalDate discountStart = LocalDate.now().minusDays(10);
        LocalDate discountEnd = LocalDate.now().plusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        BigDecimal loyaltyDiscountRate = BigDecimal.valueOf(0.1);
        BigDecimal expectedPrice = BigDecimal.ZERO;
        Product product = new Product(label, price);
        Discount discount = new Discount(discountStart, discountEnd, discountRate);
        LoyaltyDiscount loyaltyDiscount = new LoyaltyDiscount(loyaltyDiscountRate);
        product.setDiscount(discount);

        // When
        product.setLoyaltyDiscount(loyaltyDiscount);

        // Then
        assertTrue(product.getLoyaltyDiscountPriceReduction().compareTo(expectedPrice) == 0);
    }
}
