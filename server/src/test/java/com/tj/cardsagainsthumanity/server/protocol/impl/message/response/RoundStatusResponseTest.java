package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundStatus;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.body.LoginResponseBody;
import com.tj.cardsagainsthumanity.server.protocol.io.impl.JSONSerializer;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.junit.Test;

import static org.junit.Assert.*;


public class RoundStatusResponseTest {


        @Test
        public void testEmptyConstructor() {
            Response response = new RoundStatusResponse();
            assertEquals(response.getStatus(), 204);
            assertEquals(response.getStatusMessage(), "No Content");
            assertFalse(response.isErrorResponse());
        }

        @Test
        public void testJsonSerialization() {
            JSONSerializer serializer = new JSONSerializer();
            RoundStatus roundStatus = new RoundStatus();
            RoundStatusResponse command = new RoundStatusResponse("someId", roundStatus);
            String expectedData = "{\"messageType\":\"RESPONSE\",\"bodyText\":\"{\\\"status\\\":200,\\\"statusMessage\\\":\\\"OK\\\",\\\"messageId\\\":\\\"someId\\\",\\\"body\\\":{\\\"id\\\":null,\\\"blackCard\\\":null,\\\"czar\\\":null,\\\"czarIsYou\\\":false,\\\"over\\\":false,\\\"winner\\\":null,\\\"allCardsIn\\\":false,\\\"cardsInPlay\\\":null}}\"}";
            String serializedData = serializer.serializeMessage(command);
            assertEquals(serializedData, expectedData);
        }


        @Test
        public void testJsonDeserialization() {
            JSONSerializer serializer = new JSONSerializer();
            String serializedData = "{\"messageType\":\"RESPONSE\",\"bodyText\":\"{\\\"body\\\":{\\\"id\\\":56,\\\"blackCard\\\":null,\\\"czar\\\":\\\"czar\\\",\\\"czarIsYou\\\":false,\\\"over\\\":false,\\\"winner\\\":null,\\\"allCardsIn\\\":false,\\\"cardsInPlay\\\":null},\\\"status\\\":200,\\\"statusMessage\\\":\\\"OK\\\"}\"}";
            RoundStatusResponse gameResponse = serializer.deserializeMessage(serializedData, RoundStatusResponse.class);
            assertEquals(gameResponse.getStatus(), 200);
            assertEquals(gameResponse.getStatusMessage(), "OK");
            assertEquals(gameResponse.getBody().getCzar(), "czar");
            assertEquals(gameResponse.getBody().getId().intValue(), 56);
        }

    }

