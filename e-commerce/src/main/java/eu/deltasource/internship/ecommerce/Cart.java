package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

/** TO DO Cart class implementation */
public class Cart {

    private final int MORE_THAN = 1;
    private final BigDecimal DELIVERY_FEE_0 = BigDecimal.ZERO;
    private final BigDecimal DELIVERY_FEE_5 = BigDecimal.valueOf(5);
    private final BigDecimal DELIVERY_FEE_10 = BigDecimal.valueOf(10);
    private final BigDecimal DELIVERY_BOUND_100 = BigDecimal.valueOf(100);
    private final BigDecimal DELIVERY_BOUND_200 = BigDecimal.valueOf(200);
    private final int ZERO = 0;

    private List<CartItem> cartItems;
    private BigDecimal deliveryFee;

    public Cart() {
        cartItems = new ArrayList<CartItem>();
        deliveryFee = DELIVERY_FEE_10;
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
    private BigDecimal calculatePrice() {
        return cartItems.stream().map(item -> item.calculatePrice()).
                reduce(BigDecimal.ZERO, (current, next) -> current.add(next));
    }

    /** This method calculates the overall price of items in cart including VAT and delivery fee */
    public BigDecimal calculateTotalPrice(boolean isLoyaltyCardActive) {
        BigDecimal currentCalculatedPrice = calculatePrice();
        BigDecimal discount = BigDecimal.ZERO;

        if (isLoyaltyCardActive) {
            discount = getLoyaltyCardDiscount();
        }

        currentCalculatedPrice = currentCalculatedPrice.subtract(discount);

        if (currentCalculatedPrice.compareTo(DELIVERY_BOUND_200) == MORE_THAN) {
            deliveryFee = DELIVERY_FEE_0;
        }
        else if (currentCalculatedPrice.compareTo(DELIVERY_BOUND_100) == MORE_THAN) {
            deliveryFee = DELIVERY_FEE_5;
        }
        else {
            deliveryFee = DELIVERY_FEE_10;
        }

        return currentCalculatedPrice.add(deliveryFee);
    }

    /** Method used for adding new items to cart (Parameter item comes from upper app layer) */
    public void addItem(CartItem itemToAdd) {
        if (itemToAdd == null) {
            throw new IllegalArgumentException("Item cannot be null");
        }

        boolean productExistsInCart = cartItems.stream().
                anyMatch(cartItem -> cartItem.getProduct().getLabel() == itemToAdd.getProduct().getLabel());

        if (productExistsInCart) {
            throw new IllegalArgumentException("Item with this product already exists in cart");
        }

        cartItems.add(itemToAdd);
    }

    public void updateItem(int itemIndex, int newQuantity) {
        if (itemIndex < ZERO || itemIndex > cartItems.stream().count()) {
            throw new IllegalArgumentException("Index is out of range");
        }

        CartItem productToUpdate = cartItems.get(itemIndex);
        productToUpdate.setQuantity(newQuantity);
    }

    /** Remove item by entire object */
    public void removeItem(int itemIndex) {
        if (itemIndex < ZERO || itemIndex > cartItems.stream().count()) {
            throw new IllegalArgumentException("Index is out of range");
        }

        CartItem productToRemove = cartItems.get(itemIndex);
        cartItems.remove(productToRemove);
    }

    public int getNumberOfSpecialProducts () {
        return (int) cartItems.stream().
                filter(item -> item.getProduct().isDiscounted() || item.getProduct().isLoyaltyDiscounted()).
                map(item -> item.getQuantity()).reduce(0, (current, next) -> current + next);
    }

    private BigDecimal getLoyaltyCardDiscount() {
        return cartItems.stream().
                map(item -> item.getProduct().getLoyaltyDiscount().multiply(BigDecimal.valueOf(item.getQuantity()))).
                reduce(BigDecimal.ZERO, (current, next) -> current.add(next));
    }
}
