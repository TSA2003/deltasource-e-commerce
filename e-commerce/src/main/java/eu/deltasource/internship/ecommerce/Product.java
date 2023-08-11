package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Product {

    private final int LESS = -1;
    private final int EQUAL = 0;
    private final int GREATER = 1;
    private final BigDecimal LOYALTY_DISCOUNT = BigDecimal.valueOf(0.05);
    private final BigDecimal VAT_RATE = BigDecimal.valueOf(0.2);

    private String label;
    private BigDecimal price;
    private LocalDate discountStart;
    private LocalDate discountEnd;
    private BigDecimal discount;
    private int discountQuantityLimit;
    private boolean isLoyaltyCardItem;

    public Product(String label, BigDecimal price) {
        setLabel(label);
        setPrice(price);
        discountStart = LocalDate.of(2015, 1, 1);
        discountEnd = LocalDate.of(2015, 1, 2);
        discount = BigDecimal.ZERO;
        isLoyaltyCardItem = false;
    }

    public Product(String label, BigDecimal price, LocalDate discountStart,
                   LocalDate discountEnd, BigDecimal discount, boolean isLoyaltyDiscounted) {
        this(label, price);
        setLabel(label);
        setPrice(price);
        createNewDiscount(discountStart, discountEnd, discount);
        isLoyaltyCardItem = isLoyaltyDiscounted;
    }

    public String getLabel() {
        return label;
    }

    private void setLabel(String newLabel) {
        if (newLabel == null) {
            throw new IllegalArgumentException("Label cannot be null");
        }

        if (newLabel.trim().isEmpty()) {
            throw new IllegalArgumentException("Label cannot be empty");
        }

        label = newLabel;
    }

    public BigDecimal getPrice() {
        BigDecimal priceWithVat = getPriceWithVat();

        if (isDiscounted()) {
            return priceWithVat.subtract(priceWithVat.multiply(discount));
        }

        return priceWithVat;
    }

    private void setPrice(BigDecimal newPrice) {
        if(newPrice.compareTo(BigDecimal.ZERO) <= EQUAL) {
            throw new IllegalArgumentException("Cannot set negative price or zero price!");
        }

        price = newPrice;
    }

    public LocalDate getDiscountStart() {
        return discountStart;
    }

    private void setDiscountStart(LocalDate startDate) {
        discountStart = startDate;
    }

    public LocalDate getDiscountEnd() {
        return discountEnd;
    }

    private void setDiscountEnd(LocalDate endDate) {
        if (endDate.isBefore(discountStart)) {
            throw new IllegalArgumentException("Discount end date shouldn't be before start date");
        }

        discountEnd = endDate;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    private void setDiscount(BigDecimal newDiscount) {
        boolean isDiscountInRange = newDiscount.compareTo(BigDecimal.ZERO) == GREATER &&
                newDiscount.compareTo(BigDecimal.ONE) == LESS;

        if (!isDiscountInRange) {
            throw new IllegalArgumentException("Discount percentage must be number between 0 and 1");
        }

        discount = newDiscount;
    }

    public boolean isDiscounted() {
        LocalDate currentTime = Server.getCurrentTime();

        boolean isInDiscountRange = currentTime.isAfter(discountStart) &&
                currentTime.isBefore(discountEnd);

        return isInDiscountRange;
    }

    public void createNewDiscount(LocalDate discountStart, LocalDate discountEnd,
                                  BigDecimal discount, int quantityLimit) {
        setDiscountStart(discountStart);
        setDiscountEnd(discountEnd);
        setDiscount(discount);
        discountQuantityLimit = quantityLimit;
    }

    public boolean isLoyaltyDiscounted() {
        return isLoyaltyCardItem;
    }

    public void setLoyaltyDiscount(boolean isLoyaltyItem) {
        isLoyaltyCardItem = isLoyaltyItem;
    }

    public BigDecimal getLoyaltyDiscount() {
        if (!isDiscounted() && isLoyaltyCardItem) {
            return getPriceWithVat().multiply(LOYALTY_DISCOUNT);
        }

        return BigDecimal.ZERO;
    }

    private BigDecimal getPriceWithVat() {
        return price.add(price.multiply(VAT_RATE));
    }

    @Override
    public String toString() {
        return String.format("Label: %s; Price: %.2f", label, price);
    }
}
