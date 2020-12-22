package helpers.objects;

public class UserData {
    String email;
    String displayName;
    String firstName;
    String lastName;
    String password;

    public UserData(String email, String displayName, String firstName, String lastName, String password) {
        this.email = email;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getPassword() {
        return password;
    }
}
