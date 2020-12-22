package com.tj.cardsagainsthumanity.server.protocol.impl.message.response;

import com.tj.cardsagainsthumanity.server.protocol.message.Message;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class EmptyResponseTest {

    @Test
    public void testEmptyResponse() {
        EmptyResponse response = new EmptyResponse("someId", 204, "No content");
        assertEquals(response.getStatus(), 204);
        assertNull(response.getBody());
        assertEquals(response.getStatusMessage(), "No content");
        assertEquals(response.getMessageType(), Message.Type.RESPONSE);
        assertEquals(response.getMessageId(), "someId");
    }

    @Test
    public void testToString() {
        System.out.println(EmptyResponse.FORBIDDEN.toString());
    }
}