package eu.deltasource.internship.ecommerce;

import java.time.LocalDate;
import java.util.Date;

public class LoyaltyCard {

    private String id;
    private LocalDate expirationDate;

    public LoyaltyCard(String id, LocalDate expirationDate) {

    }

    public String getId() {
        return id;
    }

    public void setId(String newId) {
        if (newId == null) {
            throw new IllegalArgumentException("Id cannot be null");
        }
        if (newId.trim().isEmpty()) {
            throw new IllegalArgumentException("Id cannot be empty");
        }
        id = newId;
    }

    public LocalDate getExpirationDate() {
        return expirationDate;
    }

    public void setExpirationDate(LocalDate date) {
        if (LocalDate.now().isAfter(date)) {
            throw new IllegalArgumentException("Discount end date shouldn't be before start date");
        }
        expirationDate = date;
    }

    public boolean isActive() {
        return LocalDate.now().isBefore(expirationDate);
    }

    public void renew() {
        expirationDate = expirationDate.plusDays(30);
    }
}
