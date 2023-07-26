package ecommerce.backend;

import java.math.BigDecimal;

/** TO DO Product class implementation */
public class Product {

    private final int EQUAL = 0;

    private String label;
    private BigDecimal price;

    public Product(String label, BigDecimal price) {
        configureLabel(label);
        configurePrice(price);
    }

    public String getLabel() {
        return label;
    }

    private void configureLabel(String label) {
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

    private void configurePrice(BigDecimal price) {
        if(price.compareTo(BigDecimal.ZERO) <= EQUAL) {
            throw new IllegalArgumentException("Cannot set negative price or zero price!");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Label: %s; Price: %.2f", label, price);
    }
}
