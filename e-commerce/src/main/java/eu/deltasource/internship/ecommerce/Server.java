package eu.deltasource.internship.ecommerce;

import java.util.Date;

public class Server {
    private static Date currentTime;

    public static Date getCurrentTime() {
        return currentTime;
    }

    public static boolean isLoyaltyCardValid(String cardId) {
        return true;
    }
}
