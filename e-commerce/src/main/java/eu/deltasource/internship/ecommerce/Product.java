package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.util.Date;

public class Product {

    private final int EQUAL = 0;
    private final int ONE_HUNDRED = 100;
    private final BigDecimal LOYALTY_DISCOUNT = BigDecimal.valueOf(0.05);

    private String label;
    private BigDecimal price;
    private Date discountStart;
    private Date discountEnd;
    private BigDecimal discount;
    private boolean isLoyaltyCardItem;

    public Product(String label, BigDecimal price) {
        setLabel(label);
        setPrice(price);
        discount = BigDecimal.ZERO;
    }

    public Product(String label, BigDecimal price, Date discountStart, Date discountEnd,
                   int discountPercentage, boolean hasLoyaltyDiscount) {
        this(label, price);
        setLabel(label);
        setPrice(price);
        setDiscountStart(discountStart);
        setDiscountEnd(discountEnd);
        setDiscount(discountPercentage);
        isLoyaltyCardItem = hasLoyaltyDiscount;
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
        this.label = label;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public BigDecimal getDiscountedPrice() {
        return price.multiply(discount);
    }

    public BigDecimal getLoyaltyDiscountedPrice() {
        return price.multiply(LOYALTY_DISCOUNT);
    }

    private void setPrice(BigDecimal price) {
        if(price.compareTo(BigDecimal.ZERO) <= EQUAL) {
            throw new IllegalArgumentException("Cannot set negative price or zero price!");
        }
        this.price = price;
    }

    public Date getDiscountStart() {
        return discountStart;
    }

    private void setDiscountStart(Date discountStart) {
        this.discountStart = discountStart;
    }

    public Date getDiscountEnd() {
        return discountEnd;
    }

    private void setDiscountEnd(Date discountEnd) {
        this.discountEnd = discountEnd;
    }

    public int getDiscount() {
        return discount.intValue();
    }

    private void setDiscount(int discountPercentage) {
        BigDecimal discountPercentageAsBigDecimal = BigDecimal.valueOf(discountPercentage);
        this.discount = BigDecimal.valueOf(discountPercentage).divide(discountPercentageAsBigDecimal);
    }

    public boolean isDiscounted() {
        boolean isInDiscountRange = Server.getCurrentTime().after(discountStart) &&
                Server.getCurrentTime().before(discountEnd);

        return isInDiscountRange;
    }

    public boolean isLoyaltyDiscounted() {
        return isLoyaltyCardItem;
    }

    @Override
    public String toString() {
        return String.format("Label: %s; Price: %.2f", label, price);
    }
}
