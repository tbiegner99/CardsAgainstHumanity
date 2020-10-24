package com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body;

public class PlayerResponseBody {

    private Integer id;
    private String displayName;
    private String firstName;
    private String lastName;

    public PlayerResponseBody(Integer id, String displayName, String firstName, String lastName) {
        this.id = id;
        this.displayName = displayName;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Integer getId() {
        return id;
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
}
