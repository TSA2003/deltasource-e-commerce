package main.java.eu.deltasource.internship;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** TO DO Cart class implementation */
public class Cart {

    private final int MORE_THAN = 1;
    private final BigDecimal VAT_RATE = BigDecimal.valueOf(0.2);

    private List<CartItem> cartItems;
    private BigDecimal deliveryFee;

    public Cart() {
        cartItems = new ArrayList<CartItem>();
        deliveryFee = BigDecimal.valueOf(10);
    }

    /** Returning a read-only copy of cart items */
    public List<CartItem> getCartItems() {
        List<CartItem> temp = new ArrayList<CartItem>(cartItems);
        return Collections.unmodifiableList(temp);
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }


    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        cartItems.forEach(item -> result.append(item.toString().concat("\n")));
        result.append("Delivery fee: " + deliveryFee + "\n");

        return result.toString();
    }

    /** This method is used as a helper method to the one below which calculates total price with delivery fee */
    private BigDecimal calculatePriceWithVAT() {
        BigDecimal currentCalculatedPrice = BigDecimal.ZERO;

        for (CartItem item : cartItems) {
            BigDecimal currentItemPriceWithVAT = item.calculatePrice()
                    .add(item.calculatePrice().multiply(VAT_RATE));

            currentCalculatedPrice = currentCalculatedPrice.add(currentItemPriceWithVAT);
        }

        return currentCalculatedPrice;
    }

    /** This method calculates the overall price of items in cart including VAT and delivery fee */
    public BigDecimal calculateTotalPriceWithDeliveryFee() {
        BigDecimal currentCalculatedPrice = calculatePriceWithVAT();

        if (currentCalculatedPrice.compareTo(BigDecimal.valueOf(200)) == MORE_THAN) {
            deliveryFee = new BigDecimal(0);
        }
        else if (currentCalculatedPrice.compareTo(BigDecimal.valueOf(100)) == MORE_THAN) {
            deliveryFee = new BigDecimal(5);
        }

        return currentCalculatedPrice.add(deliveryFee);
    }

    /** Method used for adding new items to cart (Parameter item comes from upper app layer) */
    public void addItem(CartItem item) {
        if (item == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }
        boolean productExistsInCart =  cartItems.stream().
                anyMatch(cartItem -> cartItem.getProduct().getLabel() == item.getProduct().getLabel());
        if (productExistsInCart) {
            throw new IllegalArgumentException("Item with this product already exists in cart");
        }
        cartItems.add(item);
    }

    /** Method used for removing single item from cart */
    public void removeItem(String label) {
        if (label == null) {
            throw new IllegalArgumentException("Label cannot be null");
        }
        if (label.trim().isEmpty()) {
            throw new IllegalArgumentException("Label cannot be empty");
        }
        Optional<CartItem> itemToRemove =  cartItems.stream().
                filter(cartItem -> cartItem.getProduct().getLabel() == label).
                findFirst();

        if (itemToRemove.isEmpty()) {
            throw new IllegalArgumentException("Item with this product doesn't exist in cart");
        }
        cartItems.remove(itemToRemove.get());
    }

}
