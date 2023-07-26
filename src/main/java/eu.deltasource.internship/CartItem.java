package main.java.eu.deltasource.internship;

import java.math.BigDecimal;

/** TO DO CartItem class implementation */
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

    private void setProduct(Product product) {
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
            throw new IllegalArgumentException("Cannot set non-positive quantity!");
        }
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return String.format("%s Quantity: %d", product, quantity);
    }

    public BigDecimal calculatePrice() {
        return getProduct().getPrice().multiply(BigDecimal.valueOf(getQuantity()));
    }
}
