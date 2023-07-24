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
        if(quantity < 0) {
            throw new IllegalArgumentException("Cannot set negative quantity!");
        }
        this.quantity = quantity;
    }

    // Constructors
    public CartItem() {
        this.setProduct(new Product());
    }

    public CartItem(Product product, int quantity) {
        this.setProduct(product);
        this.setQuantity(quantity);
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
