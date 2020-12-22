package com.tj.cardsagainsthumanity.serializer.converter.card;

import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
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
public class CreateBlackCardRequestConverterTest {

    private final String testText = "test card text";
    private final Integer packId = 8;

    @Mock
    private CardPackageService cardPackageService;
    @Mock
    private CardPackage cardPackage;
    private CreateBlackCardRequestConverter converter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        converter = new CreateBlackCardRequestConverter(cardPackageService);
        when(cardPackageService.getCardPackageById(packId)).thenReturn(cardPackage);
    }


    @Test
    public void convertRequestToBusinessObject() {
        CreateCardRequest request = new CreateCardRequest();
        request.setPackageId(packId);
        request.setCardText(testText);

        BlackCard expectedResult = new BlackCard(cardPackage, testText);

        BlackCard result = (BlackCard) converter.convertRequestToBusinessObject(request);

        assertEquals(expectedResult, result);
    }
}
