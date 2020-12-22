package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@Ignore
public class GameResponseTest {

    @Test
    public void testEmptyConstructor() {
        Response response = new GameResponse();
        assertEquals(response.getStatus(), 0);
        assertTrue(response.isErrorResponse());
    }
/*
    @Test
    public void testJsonSerialization() {
        JSONSerializer serializer = new JSONSerializer();
        GameResponseBody gameResponse = new GameResponseBody(5, "code", Game.GameState.INITIALIZING, null, null);
        GameResponse command = new GameResponse("someId", 200, "OK", gameResponse);
        String expectedData = "{\"messageType\":\"RESPONSE\",\"bodyText\":\"{\\\"status\\\":200,\\\"statusMessage\\\":\\\"OK\\\",\\\"messageId\\\":\\\"someId\\\",\\\"body\\\":{\\\"state\\\":\\\"INITIALIZING\\\",\\\"gameId\\\":6,\\\"code\\\":\\\"someCode\\\"}}\"}";
        String serializedData = serializer.serializeMessage(command);
        assertEquals(serializedData, expectedData);
    }*/


    @Test
    public void testJsonDeserialization() {
        JSONSerializer serializer = new JSONSerializer();
        String serializedData = "{\"messageType\":\"RESPONSE\",\"bodyText\":\"{\\\"body\\\":{\\\"state\\\":\\\"INITIALIZING\\\",\\\"gameId\\\":6,\\\"code\\\":\\\"someCode\\\"},\\\"status\\\":200,\\\"statusMessage\\\":\\\"OK\\\",\\\"messageId\\\":\\\"someId\\\"}\"}";
        GameResponse gameResponse = serializer.deserializeMessage(serializedData, GameResponse.class);
        assertEquals(gameResponse.getStatus(), 200);
        assertEquals(gameResponse.getStatusMessage(), "OK");
        assertEquals(gameResponse.getBody().getCode(), "someCode");
        assertEquals(gameResponse.getBody().getGameId().intValue(), 6);
        assertEquals(gameResponse.getBody().getState(), Game.GameState.INITIALIZING);
        assertEquals(gameResponse.getMessageId(), "someId");
    }
}