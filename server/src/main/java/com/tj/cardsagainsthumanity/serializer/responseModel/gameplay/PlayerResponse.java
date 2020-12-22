package com.tj.cardsagainsthumanity.serializer.responseModel.gameplay;

import java.util.Objects;

public class PlayerResponse {
    private Integer id;
    private String email;
    private String displayName;
    private String firstName;
    private String lastName;

    public static PlayerResponseBuilder builder() {
        return new PlayerResponseBuilder();
    }

    public Integer getId() {
        return id;
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

    @Override
    public boolean equals(Object o) {
        PlayerResponse that = (PlayerResponse) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getEmail(), that.getEmail()) &&
                Objects.equals(getDisplayName(), that.getDisplayName()) &&
                Objects.equals(getFirstName(), that.getFirstName()) &&
                Objects.equals(getLastName(), that.getLastName());
    }

    public static class PlayerResponseBuilder {
        private final PlayerResponse player;

        PlayerResponseBuilder() {
            this.player = new PlayerResponse();
        }

        public PlayerResponseBuilder id(Integer id) {
            player.id = id;
            return this;
        }

        public PlayerResponseBuilder displayName(String displayName) {
            player.displayName = displayName;
            return this;
        }

        public PlayerResponseBuilder email(String email) {
            player.email = email;
            return this;
        }

        public PlayerResponseBuilder firstName(String firstName) {
            player.firstName = firstName;
            return this;
        }

        public PlayerResponseBuilder lastName(String lastName) {
            player.lastName = lastName;
            return this;
        }

        public PlayerResponse build() {
            return player;
        }
    }
}
