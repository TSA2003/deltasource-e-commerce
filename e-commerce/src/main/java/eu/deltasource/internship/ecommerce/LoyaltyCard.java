package eu.deltasource.internship.ecommerce;

import java.time.LocalDate;
import java.util.Date;

public class LoyaltyCard {

    public static final int DISCOUNT_PERCENTAGE = 5;

    private String id;
    private LocalDate expirationDate;



    public String getId() {
        return id;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void renew() {
        expirationDate = expirationDate.plusDays(30);
    }
}
