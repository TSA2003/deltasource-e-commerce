package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Product {

    private static final BigDecimal VAT_RATE = BigDecimal.valueOf(0.2);

    private String label;
    private BigDecimal price;
    private Discount discount;
    private LoyaltyDiscount loyaltyDiscount;

    public Product(String label, BigDecimal price) {
        setLabel(label);
        setPrice(price);
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
        return getPriceWithVat();
    }

    private void setPrice(BigDecimal newPrice) {
        if(newPrice.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Cannot set negative price or zero price!");
        }
        price = newPrice;
    }

    public boolean isDiscounted() {
        if (discount == null) {
            return false;
        }
        LocalDate currentTime = LocalDate.now();

        return currentTime.isAfter(discount.getStartDate()) &&
                currentTime.isBefore(discount.getEndDate());
    }

    public void setDiscount(Discount newDiscount) {
        if (newDiscount == null) {
            throw new IllegalArgumentException("Discount cannot be null");
        }
        discount = newDiscount;
    }

    public boolean isLoyaltyDiscounted() {
        return loyaltyDiscount != null;
    }

    public void setLoyaltyDiscount(LoyaltyDiscount newLoyaltyDiscount) {
        if (newLoyaltyDiscount == null) {
            throw new IllegalArgumentException("Discount cannot be null");
        }
        loyaltyDiscount = newLoyaltyDiscount;
    }

    public BigDecimal getDiscountedPrice() {
        if (isDiscounted()) {
            return getPriceWithVat().subtract(getPriceWithVat().multiply(discount.getRate()));
        }

        return getPrice();
    }

    public BigDecimal getLoyaltyDiscountPriceReduction() {
        if (isDiscounted() || !isLoyaltyDiscounted()) {
            return BigDecimal.ZERO;
        }

        return getPriceWithVat().multiply(loyaltyDiscount.getRate());
    }

    private BigDecimal getPriceWithVat() {
        return price.add(price.multiply(VAT_RATE));
    }

    @Override
    public String toString() {
        return String.format("Label: %s; Price: %.2f", label, price);
    }
}
