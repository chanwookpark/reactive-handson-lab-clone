package reactvie;

/**
 * @author chanwook
 */
public class User {

    public static final User CHANWOOK = new User("chanwook", "park");

    public static final User SEOJIN = new User("seojin", "park");

    public static final User WOOJIN = new User("woojin", "park");

    public static final User HYUNJOO = new User("hyunjoo", "kim");

    public static final User CHANWOOK_UPPER = new User("CHANWOOK", "PARK");

    public static final User SEOJIN_UPPER = new User("SEOJIN", "PARK");

    public static final User WOOJIN_UPPER = new User("WOOJIN", "PARK");

    public static final User HYUNJOO_UPPER = new User("HYUNJOO", "KIM");

    private String firstName;

    private String lastName;

    private String fullName;

    public User() {
    }

    public User(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;

        User user = (User) o;

        if (!firstName.equals(user.firstName)) return false;
        return lastName.equals(user.lastName);

    }

    @Override
    public int hashCode() {
        int result = firstName.hashCode();
        result = 31 * result + lastName.hashCode();
        return result;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }
}
