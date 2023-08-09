package eu.deltasource.internship.ecommerce;

import java.time.LocalDate;
import java.util.Date;

public class Server {
    private static LocalDate currentTime;

    public static LocalDate getCurrentTime() {
        return currentTime;
    }

    public static boolean isLoyaltyCardValid(String cardId) {
        return true;
    }
}
