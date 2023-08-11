package eu.deltasource.internship.ecommerce;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class Server {

    private static LocalDate currentTime = LocalDate.of(2014, 1, 1);
    private static List<User> users;
    private static List<Family> families;
    private static List<LoyaltyCard> loyaltyCards;

    public static LocalDate getCurrentTime() {
        return currentTime;
    }

    public static void setCurrentTime(LocalDate newCurrentTime) {
        if (newCurrentTime.isBefore(currentTime)) {
            throw new IllegalArgumentException("The new date is before the current");
        }

        currentTime = newCurrentTime;
    }

    public static User AuthenticateUser(String username, String password) {
        Optional<User> currentUser = users.stream().
                filter(user -> user.getUsername() == username).
                findFirst();

        if (currentUser.isEmpty()) {
            throw new IllegalArgumentException("Wrong username");
        }

        boolean isPasswordCorrect = currentUser.get().getPassword() == password;
        if (!isPasswordCorrect) {
            throw new IllegalArgumentException("Wrong password");
        }

        return currentUser.get();
    }

    private static void updateUsersInfo() {
        users.forEach(user -> user.updateCurrentPeriodInfo());
    }
}
