package com.tj.cardsagainsthumanity.serializer.converter.packageImport;

import com.tj.cardsagainsthumanity.models.cards.*;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateBlackCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CreateWhiteCardRequestConverter;
import com.tj.cardsagainsthumanity.serializer.converter.cardPackage.CreatePackageSerializer;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage.CreatePackageRequest;
import com.tj.cardsagainsthumanity.serializer.requestModel.packageImport.NormalizedPackageImport;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static junit.framework.TestCase.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PackageImportSerializerTest {
    @Mock
    private CreatePackageSerializer packageSerializer;
    @Mock
    private CreateBlackCardRequestConverter blackCardRequestConverter;
    @Mock
    private CreateWhiteCardRequestConverter whiteCardRequestConverter;
    @Mock
    private CreatePackageRequest createPackageRequest;
    @Mock
    private CreateCardRequest whiteCard;
    @Mock
    private CreateCardRequest blackCard;
    @Mock
    private CardPackage convertedPackage;
    @Mock
    private WhiteCard convertedWhiteCard;
    @Mock
    private BlackCard convertedBlackCard;

    private NormalizedPackageImport request;

    private PackageImportSerializer serializer;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        serializer = new PackageImportSerializer(packageSerializer, whiteCardRequestConverter, blackCardRequestConverter);
        request = new NormalizedPackageImport();
        request.setBlackCards(Arrays.asList(blackCard, blackCard));
        request.setWhiteCards(Arrays.asList(whiteCard, whiteCard));
        request.setPackageInfo(createPackageRequest);
        when(packageSerializer.convertRequestToBusinessObject(createPackageRequest)).thenReturn(convertedPackage);
        when(blackCardRequestConverter.convertRequestToBusinessObject(blackCard)).thenReturn(convertedBlackCard);
        when(whiteCardRequestConverter.convertRequestToBusinessObject(whiteCard)).thenReturn(convertedWhiteCard);
    }

    @Test
    public void convertRequestToBusinessObject() {
        Set<Card> cardSet = new HashSet<>(Arrays.asList(convertedWhiteCard, convertedWhiteCard, convertedBlackCard, convertedBlackCard));
        PackageImport expectedResult = new PackageImport(convertedPackage, cardSet);
        PackageImport result = serializer.convertRequestToBusinessObject(request);
        //it package request to business object
        verify(packageSerializer, times(1)).convertRequestToBusinessObject(createPackageRequest);
        //it converts cards to request
        verify(blackCardRequestConverter, times(2)).convertRequestToBusinessObject(blackCard);
        verify(whiteCardRequestConverter, times(2)).convertRequestToBusinessObject(whiteCard);
        //it returns expectedResult
        assertEquals(expectedResult, result);
    }
}