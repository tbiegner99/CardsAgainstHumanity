package com.tj.cardsagainsthumanity.serializer.converter.card;

import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.serializer.ResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.cardPackage.CreatePackageSerializer;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.PackageResponse;
import org.springframework.stereotype.Component;

@Component
public class CardResponseConverter implements ResponseConverter<Card, CardResponse> {

    private CreatePackageSerializer packageSerializer;

    public CardResponseConverter(CreatePackageSerializer packageSerializer) {
        this.packageSerializer = packageSerializer;
    }

    @Override
    public CardResponse convertBusinessObjectToResponse(Card businessObject) {
        return new CardResponse(
                businessObject.getId(),
                businessObject.getText(),
                getPackageFrom(businessObject.getCardPackage())
        );
    }

    private PackageResponse getPackageFrom(CardPackage businessObject) {
        return packageSerializer.convertBusinessObjectToResponse(businessObject);
    }
}
