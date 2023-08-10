package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {

    private static final int SPECIAL_PRODUCTS_LIMIT = 5;
    private static final BigDecimal LOYALTY_CARD_ACTIVATION_LIMIT = BigDecimal.valueOf(1000);
    private static final int MONTH = 30;
    private static final int EQUAL = 0;

    private String username;
    private String password;
    private int age;
    private List<Cart> orderHistory;
    private Cart currentOrder;
    private LoyaltyCard loyaltyCard;
    private LocalDate currentPeriodStart;
    private LocalDate currentPeriodEnd;
    private BigDecimal moneySpentInPeriod;
    private Family family;

    public User(String username, String password, int age) {
        setUsername(username);
        setPassword(password);
        setAge(age);
        setCurrentPeriodStart();
        setCurrentPeriodEnd();
        moneySpentInPeriod = BigDecimal.ZERO;
        orderHistory = new ArrayList<Cart>();
        currentOrder = new Cart();
    }

    public String getUsername() {
        return username;
    }

    private void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    private void setPassword(String password) {
        this.password = password;
    }

    public int getAge() {
        return age;
    }

    private void setAge(int age) {
        this.age = age;
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

    public LocalDate getCurrentPeriodStart() {
        return currentPeriodStart;
    }

    private void setCurrentPeriodStart() {
        currentPeriodStart = Server.getCurrentTime();
    }

    public LocalDate getCurrentPeriodEnd() {
        return currentPeriodEnd;
    }

    public void setCurrentPeriodEnd() {
        currentPeriodEnd = currentPeriodStart.plusDays(MONTH);
    }

    public BigDecimal getMoneySpentInPeriod() {
        return moneySpentInPeriod;
    }

    public void addItemToCart(CartItem item) {
        if (isSpecialProductLimitReached()) {
            throw new IllegalArgumentException();
        }

        currentOrder.addItem(item);
    }

    public void updateExistingItemQuantity(int itemIndex, int newQuantity) {
        currentOrder.updateItem(itemIndex, newQuantity);
    }

    public void removeExistingItem(int itemIndex) {
        currentOrder.removeItem(itemIndex);
    }

    public boolean isLoyaltyCardPresent() {
        return loyaltyCard != null;
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

    public void completeOrder(String loyaltyCardId) {
        boolean loyaltyCardUsed = false;

        if (loyaltyCardId == loyaltyCard.getId()) {
            loyaltyCardUsed = true;
        }


        BigDecimal price = currentOrder.calculateTotalPrice(loyaltyCardUsed);
        moneySpentInPeriod.add(price);

        if (isPeriodTargetMoneyReached()) {
            configureLoyaltyCard();
        }

        Cart legacyOrder = new Cart();
        copyCurrentOrderItems(legacyOrder);
        orderHistory.add(legacyOrder);

        currentOrder = new Cart();
    }

    private boolean isSpecialProductLimitReached() {
        return currentOrder.getNumberOfSpecialProducts() >= SPECIAL_PRODUCTS_LIMIT;
    }

    private boolean isPeriodTargetMoneyReached() {
        return moneySpentInPeriod.compareTo(LOYALTY_CARD_ACTIVATION_LIMIT) >= EQUAL;
    }

    private void copyCurrentOrderItems(Cart destination) {
        currentOrder.getCartItems().forEach(item -> destination.addItem(item));
    }

    public void updateCurrentPeriodInfo() {
        LocalDate currentTime = Server.getCurrentTime();
        if (currentTime.isAfter(currentPeriodEnd)) {
            configureLoyaltyCard();
            currentPeriodStart = currentTime;
            currentPeriodEnd = currentTime.plusDays(30);
            moneySpentInPeriod = BigDecimal.ZERO;
        }
    }
}
