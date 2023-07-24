package eCommerce.backend;

import java.math.BigDecimal;

/** TO DO Product */
public class Product {

    public final int LESS = -1;

    private String label;

    // Note BigDecimal type! Used for price precision
    private BigDecimal price;

    public Product(String label, BigDecimal price) {
        setLabel(label);
        setPrice(price);
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        if (label.isEmpty()) {
            throw new IllegalArgumentException("Label cannot be empty");
        }
        this.label = label;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        if(price.compareTo(BigDecimal.valueOf(0)) == LESS) {
            throw new IllegalArgumentException("Cannot set negative price!");
        }
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("Label: %s; Price: %.2f", label, price);
    }
}
