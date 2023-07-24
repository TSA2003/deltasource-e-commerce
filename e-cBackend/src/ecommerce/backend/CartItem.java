package eCommerce.backend;

import java.math.BigDecimal;

/** TO DO CartItem */
public class CartItem {

    private Product product;
    private int quantity;

    public CartItem(Product product, int quantity) {
        setProduct(product);
        setQuantity(quantity);
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        if (product == null) {
            throw new IllegalArgumentException("Product must be initialized");
        }
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Cannot set negative quantity!");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        // Using product.toString() in current toString();
        return String.format("%s Quantity: %d", product.toString(), quantity);
    }

    // Method used for calculating cart item price (product.price * product.quantity)
    public BigDecimal calculatePrice() {
        return product.getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }
}
