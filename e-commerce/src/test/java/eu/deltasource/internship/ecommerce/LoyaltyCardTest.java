package eu.deltasource.internship.ecommerce;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.concurrent.atomic.AtomicReference;

public class LoyaltyCardTest {

    @Test
    public void testLoyaltyCardCannotHaveEmptyId() {
        // Given
        String id = "";
        LocalDate expirationDate = LocalDate.now().plusDays(10);
        AtomicReference<LoyaltyCard> card = new AtomicReference<LoyaltyCard>();

        // When
        assertThrows(IllegalArgumentException.class,
                () -> card.set(new LoyaltyCard(id, expirationDate)));

        // Then
        assertNull(card.get());
    }
}
