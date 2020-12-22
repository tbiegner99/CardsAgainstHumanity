package com.tj.cardsagainsthumanity.serializer.converter.cardPackage;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage.CreatePackageRequest;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.PackageResponse;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.runners.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;

@RunWith(MockitoJUnitRunner.class)
public class CreatePackageSerializerTest {

    private final String packageName = "testPackage";
    private final String packageIcon = "icon";
    private final CardPackage.IconType iconType = CardPackage.IconType.SVG;
    private CreatePackageSerializer converter;

    @Before
    public void setUp() throws Exception {
        converter = new CreatePackageSerializer();

    }

    @Test
    public void convertRequestToBusinessObject() {
        CreatePackageRequest request = new CreatePackageRequest();
        request.setPackageName(packageName);
        request.setIcon(packageIcon);
        request.setIconType(iconType);

        CardPackage expectedResult = new CardPackage(packageName, packageIcon, iconType);

        CardPackage result = converter.convertRequestToBusinessObject(request);

        assertEquals(expectedResult, result);
    }

    @Test
    public void convertBusinessObjectToResponse() {
        CardPackage pack = new CardPackage(packageName, packageIcon, iconType);
        PackageResponse expected = new PackageResponse(1, packageName, 8, iconType, packageIcon, null, null);
        PackageResponse result = converter.convertBusinessObjectToResponse(pack);
        assertEquals(expected, result);
    }
}