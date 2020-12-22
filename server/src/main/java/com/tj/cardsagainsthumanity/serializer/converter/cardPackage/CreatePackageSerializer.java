package com.tj.cardsagainsthumanity.serializer.converter.cardPackage;

import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
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
        cardPackage.setOfficial(cardPackage.isOfficial());
        cardPackage.setOwner(objectToConvert.getOwner());
        return cardPackage;
    }


    @Override
    public PackageResponse convertBusinessObjectToResponse(CardPackage businessObject) {
        Player owner = businessObject.getOwner();

        return new PackageResponse(
                businessObject.getId(),
                businessObject.getName(),
                owner == null ? null : owner.getId(),
                businessObject.getIconType(),
                businessObject.getIcon(),
                businessObject.getWhiteCardCount(),
                businessObject.getBlackCardCount()
        );
    }
}
