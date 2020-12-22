package com.tj.cardsagainsthumanity.serializer.converter.card;

import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.serializer.RequestConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.card.CreateCardRequest;
import com.tj.cardsagainsthumanity.services.CardPackageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CreateWhiteCardRequestConverter implements RequestConverter<CreateCardRequest, WhiteCard> {

    private CardPackageService packageService;

    public CreateWhiteCardRequestConverter(@Autowired CardPackageService packageService) {
        this.packageService = packageService;
    }


    @Override
    public WhiteCard convertRequestToBusinessObject(CreateCardRequest objectToConvert) {
        Integer packageId = objectToConvert.getPackageId();
        String text = objectToConvert.getCardText();

        return new WhiteCard(null, text);
    }


}
