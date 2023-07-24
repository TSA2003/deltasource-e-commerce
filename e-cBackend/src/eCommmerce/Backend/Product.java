package eCommmerce.Backend;

import java.math.BigDecimal;

public class Product {

    // Class fields
    protected String label;

    // Note BigDecimal type! Used for price precision
    protected BigDecimal price;

    // Getters and setters
    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    // Constructor
    public Product(String label, BigDecimal price) {
        this.label = label;
        this.price = price;
    }

    // Overriding toString() method from Object
    @Override
    public String toString() {
        return String.format("Label: %s; Price: %.2f", label, price);
    }
}
