package eu.deltasource.internship.ecommerce;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class User {

    private static final int SPECIAL_PRODUCTS_LIMIT = 5;
    private static final BigDecimal CARD_ACTIVATION_LIMIT = BigDecimal.valueOf(1000);
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

    public User(String username, String password, int age) {
        setUsername(username);
        setPassword(password);
        setAge(age);
        setCurrentPeriodStart();
        setCurrentPeriodEnd();
        setMoneySpentInPeriod(BigDecimal.ZERO);
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
        currentPeriodStart = currentPeriodStart;
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

    private void setMoneySpentInPeriod(BigDecimal moneySpentInPeriod) {
        moneySpentInPeriod = moneySpentInPeriod;
    }

    public void addItemToCart(CartItem item) {
        if (isSpecialProductLimitReached()) {
            throw new IllegalArgumentException();
        }

        currentOrder.addItem(item);
    }

    public boolean isLoyaltyCardPresent() {
        return loyaltyCard != null;
    }

    private void configureLoyaltyCard() {
        if (moneySpentInPeriod.compareTo(CARD_ACTIVATION_LIMIT) >= EQUAL) {
            if (isLoyaltyCardPresent()) {

            } else {
                loyaltyCard = new LoyaltyCard();
            }
        }
    }

    public void completeOrder(String loyaltyCardId) {
        boolean loyaltyCardUsed = false;

        if (loyaltyCardId == loyaltyCard.getId()) {
            loyaltyCardUsed = true;
        }


        BigDecimal price = currentOrder.calculateTotalPrice(loyaltyCardUsed);
        moneySpentInPeriod.add(price);



        orderHistory.add(currentOrder);
        currentOrder = null;
    }

    private boolean isSpecialProductLimitReached() {
        return currentOrder.getNumberOfSpecialProducts() >= SPECIAL_PRODUCTS_LIMIT;
    }

    public void resetCurrentPeriod() {
        
    }
}
