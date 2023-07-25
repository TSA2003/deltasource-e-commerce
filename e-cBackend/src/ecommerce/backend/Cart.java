package ecommerce.backend;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/** TO DO Cart */
public class Cart {

    public final int LESS = -1;

    private List<CartItem> cartItems;
    private BigDecimal deliveryFee;

    // Returning a read-only copy of cart items
    public List<CartItem> getCartItems() {
        List<CartItem> temp = new ArrayList<CartItem>(cartItems);
        return Collections.unmodifiableList(temp);
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    public Cart() {
        cartItems = new ArrayList<CartItem>();
        deliveryFee = BigDecimal.ZERO;
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        cartItems.forEach(item -> result.append(item.toString().concat("\n")));
        result.append("Delivery fee: " + deliveryFee + "\n");

        return result.toString();
    }

    public BigDecimal calculatePriceWithVAT() {
        BigDecimal result = BigDecimal.ZERO;
        for (CartItem item : cartItems) {
            result = result.add(item.calculatePrice()).add(item.calculatePrice().multiply(BigDecimal.valueOf(0.2)));
        }

        return result;
    }

    public BigDecimal calculateTotalWithFee() {
        BigDecimal result = calculatePriceWithVAT();

        if (result.compareTo(BigDecimal.valueOf(101)) == LESS) {
            deliveryFee = new BigDecimal(10);
        }
        else if (result.compareTo(BigDecimal.valueOf(201)) == LESS) {
            deliveryFee = new BigDecimal(5);
        }

        return result.add(deliveryFee);
    }

    // Method used for adding new items to cart (Parameter item comes from upper app layer)
    public void addItem(CartItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        if (cartItems.stream().filter(cartItem -> cartItem.getProduct().getLabel() == item.getProduct().getLabel()).count() > 0) {
            throw new IllegalArgumentException("Item already exists in cart");
        }
        cartItems.add(item);
    }

    // Method used for removing single item from cart
    public void removeItem(int index) {
        if (index < 0) {
            throw new IllegalArgumentException("Index cannot be negative");
        }
        cartItems.remove(index);
    }

}
