package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

public class User {

    private static final int MONTH = 30;
    private static final int EQUAL = 0;
    private static final int MINIMUM_AGE = 8;

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
        username = newUsername;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String newPassword) {
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

    public LoyaltyCard getCard() {
        return loyaltyCard;
    }

    public boolean isLoyaltyCardPresent() {
        return loyaltyCard != null;
    }

    public ShoppingPeriod getShoppingPeriod() {
        return currentPeriod;
    }

    public void setShoppingPeriod(ShoppingPeriod newPeriod) {
        currentPeriod = newPeriod;
    }

    public boolean isPartOfFamily() {
        return family != null;
    }

    public void specifyFamily(Family family) {
        this.family = family;
    }

    private void configureLoyaltyCard() {
        if (isLoyaltyCardPresent()) {
            loyaltyCard.renew();
        } else {
            loyaltyCard = new LoyaltyCard();
        }
    }

    public void completeOrder(boolean isLoyaltyCardActive) {
        boolean isDiscountActive = true;
        int currentOrderSpecialProducts = currentOrder.getNumberOfSpecialProducts();
        currentPeriod.increaseSpecialProductsBought(currentOrderSpecialProducts);
        if (currentPeriod.isSpecialProductLimitReached()) {
            isDiscountActive = false;
        }
        BigDecimal totalPrice = currentOrder.calculateTotalPrice(isLoyaltyCardActive);
        currentPeriod.increaseMoneySpent(totalPrice);
        if (currentPeriod.isPeriodTargetMoneyReached()) {
            configureLoyaltyCard();
        }
        Cart legacyOrder = new Cart();
        copyCurrentOrderItems(legacyOrder);
        orderHistory.add(legacyOrder);
        currentOrder = new Cart();
    }

    private void copyCurrentOrderItems(Cart destination) {
        currentOrder.getCartItems().forEach(item -> destination.addItem(item));
    }

}
