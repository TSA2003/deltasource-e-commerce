package eCommmerce.Backend;

import java.math.BigDecimal;

public class CartItem {

    // Class fields
    protected Product product;
    protected int quantity;

    // Getters and setters
    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    // Constructor
    public CartItem(Product product, int quantity) {
        this.product = product;
        this.quantity = quantity;
    }

    // Overriding toString() method from Object
    @Override
    public String toString() {
        // Using product.toString() in current toString();
        return String.format("%s Quantity: %d", product.toString(), quantity);
    }

    // Method used for calculating cart item price (product.price * product.quantity)
    public BigDecimal calculatePrice() {
        return product.price.multiply(BigDecimal.valueOf(this.quantity));
    }
}
