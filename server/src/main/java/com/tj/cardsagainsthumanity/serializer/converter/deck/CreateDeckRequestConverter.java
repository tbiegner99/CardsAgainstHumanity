package com.tj.cardsagainsthumanity.serializer.converter.deck;

import com.tj.cardsagainsthumanity.models.cards.DeckInfo;
import com.tj.cardsagainsthumanity.serializer.RequestConverter;
import com.tj.cardsagainsthumanity.serializer.requestModel.deck.CreateDeckRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateDeckRequestConverter implements RequestConverter<CreateDeckRequest, DeckInfo> {

    @Override
    public DeckInfo convertRequestToBusinessObject(CreateDeckRequest objectToConvert) {
        DeckInfo ret = new DeckInfo();
        ret.setName(objectToConvert.getName());
        ret.setPlayer(objectToConvert.getOwner());
        return ret;
    }
}
