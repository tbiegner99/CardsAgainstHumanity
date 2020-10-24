package com.tj.cardsagainsthumanity.serializer.converter.card;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.serializer.converter.cardPackage.CreatePackageSerializer;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.PackageResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CardResponseConverterTest {
    private final CardPackage.IconType iconType = CardPackage.IconType.SVG;
    private final PackageResponse expectedPackage = new PackageResponse("testPackage", iconType, "testIcon");
    private final CardPackage testPackage = new CardPackage("testPackage", "testIcon", iconType);
    private final WhiteCard card = new WhiteCard(testPackage, "text");
    private CardResponseConverter converter;

    @Mock
    private CreatePackageSerializer packageConverter;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        card.setId(11);
        converter = new CardResponseConverter(packageConverter);
        when(packageConverter.convertBusinessObjectToResponse(any())).thenReturn(expectedPackage);

    }

    @Test
    public void convertBusinessObjectToResponse() {

        CardResponse expected = new CardResponse(11, "text", expectedPackage);
        CardResponse result = converter.convertBusinessObjectToResponse(card);
        //it converts package
        verify(packageConverter, times(1)).convertBusinessObjectToResponse(testPackage);
        //it creates correct result
        assertEquals(expected, result);
    }
}