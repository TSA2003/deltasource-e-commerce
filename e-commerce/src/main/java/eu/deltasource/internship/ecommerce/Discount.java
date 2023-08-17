package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Discount {

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal rate;

    public Discount(LocalDate start, LocalDate end, BigDecimal newRate) {
        setStartDate(start);
        setEndDate(end);
        setRate(newRate);
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    private void setStartDate(LocalDate start) {
        startDate = start;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    private void setEndDate(LocalDate end) {
        if (end.isBefore(startDate)) {
            throw new IllegalArgumentException("Discount end date shouldn't be before start date");
        }
        endDate = end;
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
