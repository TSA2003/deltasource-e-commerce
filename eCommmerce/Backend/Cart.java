package eCommmerce.Backend;

import java.math.BigDecimal;
import java.util.ArrayList;

public class Cart {

    // Class fields
    protected ArrayList<CartItem> cartItems;
    protected BigDecimal deliveryFee;

    // Getters (ONLY ONE GETTER!)
    // The class user shouldn't receive a reference to cart items (user can manipulate them)
    // The delivery fee is set by the calculateOverall method
    public BigDecimal getDeliveryFee() {
        return deliveryFee;
    }

    // Constructor
    public Cart() {
        cartItems = new ArrayList<CartItem>();
    }

    // Overriding toString() method from Object
    // This might look a bit complicated (BUT IT ISN'T)
    @Override
    public String toString() {
        // We need to create a variable which stores the output
        // The reason is we have a list of objects and we need to call their toString() method individually
        StringBuilder result = new StringBuilder();
        for (CartItem item : cartItems) {
            // Adding data for each cart item to the result (EVERY DIFFERENT ITEM IS ON NEW LINE)
            result.append(item.toString() + "\n");
        }
        // Adding the delivery fee
        result.append(deliveryFee);

        // Returning our temporary string
        return result.toString();
    }

    // This method calculates VAT
    public BigDecimal calculateVAT() {
        BigDecimal result = new BigDecimal(0);
        for (CartItem item : cartItems) {
            result.add(item.calculatePrice().divide(BigDecimal.valueOf(20)));
        }

        return result;
    }

    // This method calculates total cart price including VAT
    public BigDecimal calculateTotal() {
        BigDecimal result = new BigDecimal(0);
        for (CartItem item : cartItems) {
            result.add(item.calculatePrice().divide(BigDecimal.valueOf(20)));
        }

        if (result.compareTo(BigDecimal.valueOf(100)) == -1) {
            deliveryFee = new BigDecimal(10);
        }
        else if (result.compareTo(BigDecimal.valueOf(200)) == -1) {
            deliveryFee = new BigDecimal(5);
        }
        else {
            deliveryFee = new BigDecimal(0);
        }

        return result.add(calculateVAT()).add(deliveryFee);
    }

    // Method used for adding new items to cart (Parameter item comes from upper app layer)
    public void addItem(CartItem item) {
        cartItems.add(item);
    }

    // Method used for removing single item from cart
    public void removeItem(int index) {
        cartItems.remove(index);
    }
    
}
