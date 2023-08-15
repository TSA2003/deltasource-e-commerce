package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

public class ShoppingPeriodTest {

    @Test
    public void testDiscountEndDateCannotBeBeforeStartDate() {
        // Given
        LocalDate periodStart = LocalDate.now();
        LocalDate periodEnd = periodStart.minusDays(10);
        AtomicReference<ShoppingPeriod> period = new AtomicReference<ShoppingPeriod>();

        // When
        assertThrows(IllegalArgumentException.class,
                () -> period.set(new ShoppingPeriod(periodStart, periodEnd)));

        // Then
        assertNull(period.get());
    }

    @Test
    public void testIsTargetMoneyReached() {
        // Given
        LocalDate periodStart = LocalDate.now();
        LocalDate periodEnd = periodStart.plusDays(10);
        ShoppingPeriod period = new ShoppingPeriod(periodStart, periodEnd);
        BigDecimal moneySpent = BigDecimal.valueOf(1000);

        // When
        period.increaseMoneySpent(moneySpent);

        // Then
        assertTrue(period.isTargetMoneyReached());
    }

    @Test
    public void testIsSpecialProductLimitReached() {
        // Given
        LocalDate periodStart = LocalDate.now();
        LocalDate periodEnd = periodStart.plusDays(10);
        ShoppingPeriod period = new ShoppingPeriod(periodStart, periodEnd);
        int specialProductNumber = 101;

        // When
        period.increaseSpecialProductsBought(specialProductNumber);

        // Then
        assertTrue(period.isSpecialProductLimitReached());
    }
}
