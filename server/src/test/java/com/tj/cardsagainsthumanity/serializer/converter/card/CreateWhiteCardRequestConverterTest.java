package com.tj.cardsagainsthumanity.serializer.converter.card;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.services.CardPackageService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CreateWhiteCardRequestConverterTest {

    private final String testText = "test card text";
    private final Integer packId = 8;

    @Mock
    private CardPackageService cardPackageService;
    @Mock
    private CardPackage cardPackage;
    private CreateWhiteCardRequestConverter converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new CreateWhiteCardRequestConverter(cardPackageService);
        when(cardPackageService.getCardPackageById(packId)).thenReturn(cardPackage);
    }


    @Test
    public void convertRequestToBusinessObject() {
        CreateCardRequest request = new CreateCardRequest();
        request.setPackageId(packId);
        request.setCardText(testText);

        WhiteCard expectedResult = new WhiteCard(cardPackage, testText);

        WhiteCard result = converter.convertRequestToBusinessObject(request);

        assertEquals(expectedResult, result);
    }
}
