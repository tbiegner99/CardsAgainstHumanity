package com.tj.cardsagainsthumanity.serializer.requestModel.user;

public class PlayerRegistrationRequest {
    private String email;
    private String displayName;
    private String password;
    private String firstName;
    private String lastName;

    public PlayerRegistrationRequest() {
    }

    public PlayerRegistrationRequest(String email, String displayName, String password, String firstName, String lastName) {
        this();
        setEmail(email);
        setDisplayName(displayName);
        setPassword(password);
        setFirstName(firstName);
        setLastName(lastName);
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
