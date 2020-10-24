package com.tj.cardsagainsthumanity.serializer.converter.cardPackage;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.serializer.Serializer;
import com.tj.cardsagainsthumanity.serializer.requestModel.cardPackage.CreatePackageRequest;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.PackageResponse;
import org.springframework.stereotype.Component;

@Component
public class CreatePackageSerializer implements Serializer<CreatePackageRequest, CardPackage, PackageResponse> {

    @Override
    public CardPackage convertRequestToBusinessObject(CreatePackageRequest objectToConvert) {
        CardPackage cardPackage = new CardPackage();
        cardPackage.setName(objectToConvert.getPackageName());
        cardPackage.setIconType(objectToConvert.getIconType());
        cardPackage.setIcon(cardPackage.getIcon());
        return cardPackage;
    }


    @Override
    public PackageResponse convertBusinessObjectToResponse(CardPackage businessObject) {

        return new PackageResponse(
                businessObject.getName(),
                businessObject.getIconType(),
                businessObject.getIcon()
        );
    }
}
