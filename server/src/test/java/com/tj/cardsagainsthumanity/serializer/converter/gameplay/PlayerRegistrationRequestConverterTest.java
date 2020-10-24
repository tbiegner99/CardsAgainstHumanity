package com.tj.cardsagainsthumanity.serializer.converter.gameplay;

import com.tj.cardsagainsthumanity.models.gameplay.Credentials;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.serializer.converter.user.PlayerRegistrationRequestConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.user.PlayerRegistrationRequest;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;
import org.springframework.security.crypto.password.PasswordEncoder;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayerRegistrationRequestConverterTest {

    private final String displayName = "testDisplay";
    private final String firstName = "testDisplay";
    private final String lastName = "testDisplay";
    private final String email = "testEmail";
    private final String password = "testPassword";

    @Mock
    private PasswordEncoder encoder;

    private PlayerRegistrationRequestConverter converter;
    private PlayerRegistrationRequest testRequest;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new PlayerRegistrationRequestConverter(encoder);
        when(encoder.encode(any())).thenReturn("convertedPassword");

        testRequest = new PlayerRegistrationRequest(email, displayName, password, firstName, lastName);

    }

    @Test
    public void convertRequestToBusinessObject() {
        Player result = converter.convertRequestToBusinessObject(testRequest);
        Player expectedResult = new Player();
        expectedResult.setFirstName(firstName);
        expectedResult.setLastName(lastName);
        expectedResult.setDisplayName(displayName);
        expectedResult.setEmail(email);
        expectedResult.setCredentials(new Credentials("convertedPassword"));

        verify(encoder, times(1)).encode(password);

        assertEquals(result, expectedResult);
    }
}
