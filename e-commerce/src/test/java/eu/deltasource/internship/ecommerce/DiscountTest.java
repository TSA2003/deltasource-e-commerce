package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

public class DiscountTest {

    @Test
    public void testDiscountEndDateCannotBeBeforeStartDate() {
        // Given
        LocalDate discountStart = LocalDate.now();
        LocalDate discountEnd = discountStart.minusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(0.1);
        AtomicReference<Discount> discount = new AtomicReference<Discount>();

        // When
        assertThrows(IllegalArgumentException.class,
                () -> discount.set(new Discount(discountStart, discountEnd, discountRate)));

        // Then
        assertNull(discount.get());
    }

    @Test
    public void testDiscountCannotBeBelow0() {
        // Given
        LocalDate discountStart = LocalDate.now();
        LocalDate discountEnd = discountStart.plusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(-0.1);
        AtomicReference<Discount> discount = new AtomicReference<Discount>();

        // When
        assertThrows(IllegalArgumentException.class,
                () -> discount.set(new Discount(discountStart, discountEnd, discountRate)));

        // Then
        assertNull(discount.get());
    }

    @Test
    public void testDiscountCannotBeAbove1() {
        // Given
        LocalDate discountStart = LocalDate.now();
        LocalDate discountEnd = discountStart.plusDays(10);
        BigDecimal discountRate = BigDecimal.valueOf(1.2);
        AtomicReference<Discount> discount = new AtomicReference<Discount>();

        // When
        assertThrows(IllegalArgumentException.class,
                () -> discount.set(new Discount(discountStart, discountEnd, discountRate)));

        // Then
        assertNull(discount.get());
    }
}
