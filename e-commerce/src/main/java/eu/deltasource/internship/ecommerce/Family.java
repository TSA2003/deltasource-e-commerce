package eu.deltasource.internship.ecommerce;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Family {
    private static int MAXIMUM_FAMILY_MEMBERS = 5;
    private static int MAXIMUM_ADULT_MEMBERS = 3;
    private static int ADOLESCENCE_AGE = 18;

    private User owner;
    private List<User> members;

    public Family() {

    }

    public List<User> getFamilyMembers() {
        List<User> temp = new ArrayList<User>(members);
        return Collections.unmodifiableList(temp);
    }

    public int getAdultMembersCount() {
        return (int) members.stream().filter(user -> user.getAge() >= ADOLESCENCE_AGE).count();
    }

    public void addMember(User newMember) {
        boolean isNewMemberAdult = newMember.getAge() >= ADOLESCENCE_AGE;

        if (members.stream().count() == MAXIMUM_FAMILY_MEMBERS) {
            throw new IllegalArgumentException("Maximum family members number reached.");
        }

        if (isNewMemberAdult && getAdultMembersCount() == MAXIMUM_ADULT_MEMBERS) {
            throw new IllegalArgumentException("Maximum adult family members number reached.");
        }

        members.add(newMember);
    }
}
