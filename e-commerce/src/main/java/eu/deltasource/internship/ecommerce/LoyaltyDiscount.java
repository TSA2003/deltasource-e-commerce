package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;

public class LoyaltyDiscount {
    private BigDecimal rate;

    public LoyaltyDiscount(BigDecimal newRate) {
        setRate(newRate);
    }

    public BigDecimal getRate() {
        return rate;
    }

    private void setRate(BigDecimal newRate) {
        boolean isDiscountInRange = newRate.compareTo(BigDecimal.ZERO) >= 0 &&
                newRate.compareTo(BigDecimal.ONE) <= 0;
        if (!isDiscountInRange) {
            throw new IllegalArgumentException("Discount rate must be number between 0 and 1");
        }
        rate = newRate;
    }
}
