package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/** TO DO Cart class implementation */
public class Cart {

    private static final BigDecimal DELIVERY_FEE_0 = BigDecimal.ZERO;
    private static final BigDecimal DELIVERY_FEE_5 = BigDecimal.valueOf(5);
    private static final BigDecimal DELIVERY_FEE_10 = BigDecimal.valueOf(10);
    private static final BigDecimal DELIVERY_BOUND_100 = BigDecimal.valueOf(100);
    private static final BigDecimal DELIVERY_BOUND_200 = BigDecimal.valueOf(200);

    private List<CartItem> cartItems;
    private BigDecimal deliveryFee;

    public Cart() {
        cartItems = new ArrayList<CartItem>();
        deliveryFee = DELIVERY_FEE_10;
    }

    /** Returning a read-only copy of cart items */
    public List<CartItem> getCartItems() {
        return Collections.unmodifiableList(cartItems);
    }

    public BigDecimal getDeliveryFee() {
        return deliveryFee;
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
        if (itemIndex < 0 || itemIndex > cartItems.stream().count()) {
            throw new IllegalArgumentException("Index is out of range");
        }
        CartItem productToUpdate = cartItems.get(itemIndex);
        productToUpdate.setQuantity(newQuantity);
    }

    /** Remove item by entire object */
    public void removeItem(int itemIndex) {
        if (itemIndex < 0 || itemIndex > cartItems.stream().count()) {
            throw new IllegalArgumentException("Index is out of range");
        }
        CartItem productToRemove = cartItems.get(itemIndex);
        cartItems.remove(productToRemove);
    }

    /** This method is used as a helper method to the one below which calculates total price with delivery fee */
    private BigDecimal calculatePrice() {
        return cartItems.stream().map(item -> item.calculatePrice()).
                reduce(BigDecimal.ZERO, (current, next) -> current.add(next));
    }

    private BigDecimal calculateDiscountedPrice() {
        return cartItems.stream().map(item -> item.calculateDiscountedPrice()).
                reduce(BigDecimal.ZERO, (current, next) -> current.add(next));
    }

    /** This method calculates the overall price of items in cart including VAT and delivery fee */
    public BigDecimal calculateTotalPrice(boolean isDiscountActive) {
        BigDecimal currentCalculatedPrice = BigDecimal.ZERO;
        if (isDiscountActive) {
            currentCalculatedPrice = calculateDiscountedPrice();
        } else {
            currentCalculatedPrice = calculatePrice();
        }
        if (currentCalculatedPrice.compareTo(DELIVERY_BOUND_200) == 1) {
            deliveryFee = DELIVERY_FEE_0;
        }
        else if (currentCalculatedPrice.compareTo(DELIVERY_BOUND_100) == 1) {
            deliveryFee = DELIVERY_FEE_5;
        }
        else {
            deliveryFee = DELIVERY_FEE_10;
        }

        return currentCalculatedPrice.add(deliveryFee);
    }

    public int getNumberOfSpecialProducts () {
        return (int) cartItems.stream().
                filter(item -> item.getProduct().isDiscounted() || item.getProduct().isLoyaltyDiscounted()).
                map(item -> item.getQuantity()).reduce(0, (current, next) -> current + next);
    }

    public BigDecimal getLoyaltyCardDiscount() {
        return cartItems.stream().
                map(item -> item.getProduct().getLoyaltyDiscountPriceReduction().multiply(BigDecimal.valueOf(item.getQuantity()))).
                reduce(BigDecimal.ZERO, (current, next) -> current.add(next));
    }

    @Override
    public String toString() {
        StringBuilder result = new StringBuilder();
        cartItems.forEach(item -> result.append(item.toString().concat("\n")));
        result.append("Delivery fee: " + deliveryFee + "\n");

        return result.toString();
    }
}
