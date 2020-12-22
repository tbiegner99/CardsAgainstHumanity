package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.LoginResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

@Ignore
public class SuccessfulLoginResponseTest {


    @Test
    public void testEmptyConstructor() {
        Response response = new SuccessfulLoginResponse();
        assertEquals(response.getStatus(), 204);
        assertEquals(response.getStatusMessage(), "No Content");
        assertFalse(response.isErrorResponse());
    }

    @Test
    public void testJsonSerialization() {
        JSONSerializer serializer = new JSONSerializer();
        LoginResponseBody loginResponse = new LoginResponseBody(56, "token");
        SuccessfulLoginResponse command = new SuccessfulLoginResponse("someId", loginResponse);
        String expectedData = "{\"messageType\":\"RESPONSE\",\"bodyText\":\"{\\\"status\\\":200,\\\"statusMessage\\\":\\\"OK\\\",\\\"messageId\\\":\\\"someId\\\",\\\"body\\\":{\\\"playerId\\\":56,\\\"token\\\":\\\"token\\\"}}\"}";
        String serializedData = serializer.serializeMessage(command);
        assertEquals(serializedData, expectedData);
    }


    @Test
    public void testJsonDeserialization() {
        JSONSerializer serializer = new JSONSerializer();
        String serializedData = "{\"messageType\":\"RESPONSE\",\"bodyText\":\"{\\\"body\\\":{\\\"playerId\\\":56,\\\"token\\\":\\\"token\\\"},\\\"status\\\":200,\\\"statusMessage\\\":\\\"OK\\\"}\"}";
        SuccessfulLoginResponse gameResponse = serializer.deserializeMessage(serializedData, SuccessfulLoginResponse.class);
        assertEquals(gameResponse.getStatus(), 200);
        assertEquals(gameResponse.getStatusMessage(), "OK");
        assertEquals(gameResponse.getBody().getToken(), "token");
        assertEquals(gameResponse.getBody().getPlayerId().intValue(), 56);
    }

}