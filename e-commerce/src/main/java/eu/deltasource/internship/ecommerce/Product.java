package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Date;

public class Product {

    private final int EQUAL = 0;
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

    public Product(String label, BigDecimal price, Date discountStart,
                   Date discountEnd, int discountPercentage, boolean isLoyaltyDiscounted) {
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

    private void setLabel(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Label cannot be null");
        }
        if (label.trim().isEmpty()) {
            throw new IllegalArgumentException("Label cannot be empty");
        }
        label = label;
    }

    public BigDecimal getPrice() {
        if (isDiscounted()) {
            return getPriceWithVAT().multiply(discount);
        }
        return getPriceWithVAT();
    }

    private void setPrice(BigDecimal price) {
        if(price.compareTo(BigDecimal.ZERO) <= EQUAL) {
            throw new IllegalArgumentException("Cannot set negative price or zero price!");
        }
        price = price;
    }

    public LocalDate getDiscountStart() {
        return discountStart;
    }

    private void setDiscountStart(LocalDate discountStart) {
        discountStart = discountStart;
    }

    public LocalDate getDiscountEnd() {
        return discountEnd;
    }

    private void setDiscountEnd(LocalDate discountEnd) {
        discountEnd = discountEnd;
    }

    public int getDiscount() {
        return discount.intValue();
    }

    private void setDiscount(int discountPercentage) {
        BigDecimal discountPercentageAsBigDecimal = BigDecimal.valueOf(discountPercentage);
        discount = BigDecimal.valueOf(discountPercentage).divide(discountPercentageAsBigDecimal);
    }

    public boolean isDiscounted() {
        boolean isInDiscountRange = Server.getCurrentTime().after(discountStart) &&
                Server.getCurrentTime().before(discountEnd);

        return isInDiscountRange;
    }

    public boolean hasLoyaltyDiscount() {
        return isLoyaltyCardItem;
    }

    public BigDecimal applyLoyaltyDiscount() {
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
