package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class User {

    private static final int MONTH = 30;
    private static final int EQUAL = 0;
    private static final int MINIMUM_AGE = 8;
    private static final int MINIMUM_PASSWORD_LENGTH = 8;

    private String username;
    private String password;
    private int age;
    private List<Cart> orderHistory;
    private Cart currentOrder;
    private LoyaltyCard loyaltyCard;
    private ShoppingPeriod currentPeriod;
    private Family family;

    public User(String username, String password, int age, ShoppingPeriod period) {
        setUsername(username);
        setPassword(password);
        setAge(age);
        setShoppingPeriod(period);
        orderHistory = new ArrayList<Cart>();
        currentOrder = new Cart();
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String newUsername) {
        if (newUsername == null) {
            throw new IllegalArgumentException("Username cannot be null");
        }
        if (newUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Username cannot be empty");
        }
        username = newUsername;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String newPassword) {
        if (newPassword.length() < MINIMUM_PASSWORD_LENGTH) {
            throw new IllegalArgumentException("Password must be at least 8 symbols long");
        }

        password = newPassword;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int newAge) {
        if (newAge < MINIMUM_AGE) {
            throw new IllegalArgumentException("The user is below required age");
        }
        age = newAge;
    }

    public List<Cart> getOrderHistory() {
        return orderHistory;
    }

    public Cart getCurrentOrder() {
        return currentOrder;
    }

    public LoyaltyCard getLoyaltyCard() {
        return loyaltyCard;
    }

    public void setLoyaltyCard(LoyaltyCard card) {
        if (card == null) {
            throw new IllegalArgumentException("Loyalty card cannot be null");
        }
        loyaltyCard = card;
    }

    public ShoppingPeriod getShoppingPeriod() {
        return currentPeriod;
    }

    public void setShoppingPeriod(ShoppingPeriod newPeriod) {
        if (newPeriod == null) {
            throw new IllegalArgumentException("Period cannot be null");
        }
        currentPeriod = newPeriod;
    }

    public boolean isPartOfFamily() {
        return family != null;
    }

    public void setFamily(Family family) {

        this.family = family;
    }

    public boolean isLoyaltyCardAvailable() {
        if (loyaltyCard == null) {
            return false;
        }

        return loyaltyCard.isActive();
    }

    public void completeOrder(boolean isLoyaltyCardChosen) {
        boolean isDiscountActive = true;
        int currentOrderSpecialProducts = currentOrder.getNumberOfSpecialProducts();
        currentPeriod.increaseSpecialProductsBought(currentOrderSpecialProducts);
        if (currentPeriod.isSpecialProductLimitReached()) {
            isDiscountActive = false;
        }
        BigDecimal totalPrice = currentOrder.calculateTotalPrice(isDiscountActive);
        if (isLoyaltyCardChosen && isLoyaltyCardAvailable()) {
            BigDecimal loyaltyCardDiscount = currentOrder.getLoyaltyCardDiscount();
            totalPrice = totalPrice.subtract(loyaltyCardDiscount);
        }
        currentPeriod.increaseMoneySpent(totalPrice);
        Cart legacyOrder = new Cart();
        copyCurrentOrderItems(legacyOrder);
        orderHistory.add(legacyOrder);
        currentOrder = new Cart();
    }

    private void copyCurrentOrderItems(Cart destination) {
        currentOrder.getCartItems().forEach(item -> destination.addItem(item));
    }

}
