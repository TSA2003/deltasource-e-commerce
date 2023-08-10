package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Product {

    private final int EQUAL = 0;
    private final int ZERO = 0;
    private final int ONE_HUNDRED = 100;
    private final BigDecimal LOYALTY_DISCOUNT = BigDecimal.valueOf(0.05);
    private final BigDecimal VAT_RATE = BigDecimal.valueOf(0.2);

    private String label;
    private BigDecimal price;
    private LocalDate discountStart;
    private LocalDate discountEnd;
    private BigDecimal discount;
    private boolean isLoyaltyCardItem;

    public Product(String label, BigDecimal price) {
        setLabel(label);
        setPrice(price);
        discount = BigDecimal.ZERO;
    }

    public Product(String label, BigDecimal price, LocalDate discountStart,
                   LocalDate discountEnd, int discountPercentage, boolean isLoyaltyDiscounted) {
        this(label, price);
        setLabel(label);
        setPrice(price);
        setDiscountStart(discountStart);
        setDiscountEnd(discountEnd);
        setDiscount(discountPercentage);
        isLoyaltyCardItem = isLoyaltyDiscounted;
    }

    public String getLabel() {
        return label;
    }

    private void setLabel(String newLabel) {
        if (label == null) {
            throw new IllegalArgumentException("Label cannot be null");
        }
        if (label.trim().isEmpty()) {
            throw new IllegalArgumentException("Label cannot be empty");
        }
        label = newLabel;
    }

    public BigDecimal getPrice() {
        if (isDiscounted()) {
            return getPriceWithVAT().multiply(discount);
        }
        return getPriceWithVAT();
    }

    private void setPrice(BigDecimal newPrice) {
        if(price.compareTo(BigDecimal.ZERO) <= EQUAL) {
            throw new IllegalArgumentException("Cannot set negative price or zero price!");
        }
        price = newPrice;
    }

    public LocalDate getDiscountStart() {
        return discountStart;
    }

    private void setDiscountStart(LocalDate startDate) {
        if (Server.getCurrentTime().isAfter(discountStart)) {
            throw new IllegalArgumentException("Discount shouldn't be set for past date");
        }
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

    public int getDiscount() {
        return discount.intValue();
    }

    private void setDiscount(int discountPercentage) {
        if (discountPercentage < ZERO || discountPercentage > ONE_HUNDRED) {
            throw new IllegalArgumentException("Discount percentage must be number between 0 and 100");
        }

        BigDecimal discountPercentageAsBigDecimal = BigDecimal.valueOf(discountPercentage);
        discount = BigDecimal.valueOf(discountPercentage).divide(discountPercentageAsBigDecimal);
    }

    public boolean isDiscounted() {
        LocalDate currentTime = Server.getCurrentTime();
        boolean isInDiscountRange = currentTime.isAfter(discountStart) &&
                currentTime.isBefore(discountEnd);

        return isInDiscountRange;
    }

    public boolean isLoyaltyDiscounted() {
        return isLoyaltyCardItem;
    }

    public BigDecimal getLoyaltyDiscount() {
        if (!isDiscounted() && isLoyaltyCardItem) {
            return getPriceWithVAT().multiply(LOYALTY_DISCOUNT);
        }

        return BigDecimal.ZERO;
    }

    private BigDecimal getPriceWithVAT() {
        return price.add(price.multiply(VAT_RATE));
    }

    @Override
    public String toString() {
        return String.format("Label: %s; Price: %.2f", label, price);
    }
}
