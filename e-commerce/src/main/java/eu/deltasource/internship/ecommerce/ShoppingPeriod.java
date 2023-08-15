package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ShoppingPeriod {

    private static final int SPECIAL_PRODUCTS_LIMIT = 100;
    private static final BigDecimal LOYALTY_CARD_ACTIVATION_LIMIT = BigDecimal.valueOf(1000);

    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal moneySpent;
    private int specialProductsBought;

    public ShoppingPeriod(LocalDate start, LocalDate end) {
        setStartDate(start);
        setEndDate(end);
        moneySpent = BigDecimal.ZERO;
        specialProductsBought = 0;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate start) {
        startDate = start;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate end) {
        if (end.isBefore(startDate)) {
            throw new IllegalArgumentException("Discount end date shouldn't be before start date");
        }
        endDate = end;
    }

    public BigDecimal getMoneySpent() {
        return moneySpent;
    }

    public void increaseMoneySpent(BigDecimal magnifier) {
        moneySpent = moneySpent.add(magnifier);
    }

    public int getSpecialProductsBought() {
        return specialProductsBought;
    }

    public void increaseSpecialProductsBought(int magnifier) {
        int newProducts = specialProductsBought + magnifier;

        specialProductsBought = specialProductsBought + magnifier;
    }

    public boolean isSpecialProductLimitReached() {
        return specialProductsBought >= SPECIAL_PRODUCTS_LIMIT;
    }

    public boolean isTargetMoneyReached() {
        return moneySpent.compareTo(LOYALTY_CARD_ACTIVATION_LIMIT) >= 0;
    }

    public boolean isExpired() {
        return LocalDate.now().isAfter(endDate);
    }
}
