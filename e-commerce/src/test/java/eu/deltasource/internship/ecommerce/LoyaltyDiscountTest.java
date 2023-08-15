package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.util.concurrent.atomic.AtomicReference;

public class LoyaltyDiscountTest {

    @Test
    public void testLoyaltyDiscountCannotBeBelow0() {
        // Given
        BigDecimal loyaltyDiscountRate = BigDecimal.valueOf(-0.1);
        AtomicReference<LoyaltyDiscount> loyaltyDiscount = new AtomicReference<LoyaltyDiscount>();

        // When
        assertThrows(IllegalArgumentException.class,
                () -> loyaltyDiscount.set(new LoyaltyDiscount(loyaltyDiscountRate)));

        // Then
        assertNull(loyaltyDiscount.get());
    }

    @Test
    public void testLoyaltyDiscountCannotBeAbove1() {
        // Given
        BigDecimal loyaltyDiscountRate = BigDecimal.valueOf(1.2);
        AtomicReference<LoyaltyDiscount> loyaltyDiscount = new AtomicReference<LoyaltyDiscount>();

        // When
        assertThrows(IllegalArgumentException.class,
                () -> loyaltyDiscount.set(new LoyaltyDiscount(loyaltyDiscountRate)));

        // Then
        assertNull(loyaltyDiscount.get());
    }
}
