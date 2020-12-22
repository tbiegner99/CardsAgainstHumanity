package com.tj.cardsagainsthumanity.serializer.converter.card;

import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.serializer.RequestConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.services.CardPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateBlackCardRequestConverter implements RequestConverter<CreateCardRequest, BlackCard> {

    private CardPackageService packageService;

    public CreateBlackCardRequestConverter(@Autowired CardPackageService packageService) {
        this.packageService = packageService;
    }

    @Override
    public BlackCard convertRequestToBusinessObject(CreateCardRequest objectToConvert) {
        Integer packageId = objectToConvert.getPackageId();
        String text = objectToConvert.getCardText();

        CardPackage packageFromId = null;
        if (packageId != null) {
            packageFromId = packageService.getCardPackageById(packageId);
        }

        return new BlackCard(packageFromId, text);
    }


}
