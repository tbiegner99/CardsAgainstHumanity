package com.tj.cardsagainsthumanity.serializer.converter.gameplay;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.PlayerResponse;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class PlayerResponseConverterTest {

    private final Integer testId = 1;
    private final String testEmail = "testEmail";
    private final String testFirstName = "testFirstName";
    private final String testLastName = "testLastName";
    private final String testDisplayName = "testDisplayName";


    private PlayerResponseConverter converter;
    private Player player;


    @Before
    public void setUp() {
        converter = new PlayerResponseConverter();
        player = new Player();
        player.setEmail(testEmail);
        player.setId(testId);
        player.setFirstName(testFirstName);
        player.setLastName(testLastName);
        player.setDisplayName(testDisplayName);

    }

    @Test
    public void convertBusinessObjectToResponse() {
        PlayerResponse response = converter.convertBusinessObjectToResponse(player);
        PlayerResponse expectedResponse = PlayerResponse.builder()
                .id(testId)
                .email(testEmail)
                .lastName(testLastName)
                .firstName(testFirstName)
                .displayName(testDisplayName)
                .build();
        assertEquals(expectedResponse, response);
    }
}