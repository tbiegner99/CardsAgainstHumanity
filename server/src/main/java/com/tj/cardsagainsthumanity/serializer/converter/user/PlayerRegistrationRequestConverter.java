package com.tj.cardsagainsthumanity.serializer.converter.user;

import com.tj.cardsagainsthumanity.models.gameplay.Credentials;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.serializer.RequestConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.user.PlayerRegistrationRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class PlayerRegistrationRequestConverter implements RequestConverter<PlayerRegistrationRequest, Player> {

    private PasswordEncoder encoder;

    public PlayerRegistrationRequestConverter(@Autowired PasswordEncoder encoder) {
        this.encoder = encoder;
    }

    @Override
    public Player convertRequestToBusinessObject(PlayerRegistrationRequest objectToConvert) {
        Credentials credentials = new Credentials();
        credentials.setPassword(encoder.encode(objectToConvert.getPassword()));

        Player result = new Player();
        result.setFirstName(objectToConvert.getFirstName());
        result.setLastName(objectToConvert.getLastName());
        result.setEmail(objectToConvert.getEmail());
        result.setDisplayName(objectToConvert.getDisplayName());
        result.setCredentials(credentials);

        return result;
    }
}
