package com.tj.cardsagainsthumanity.serializer.converter.deck;

import com.tj.cardsagainsthumanity.models.cards.DeckInfo;
import com.tj.cardsagainsthumanity.serializer.ResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.gameplay.PlayerResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DeckResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class DeckResponseConverter implements ResponseConverter<DeckInfo, DeckResponse> {
    private PlayerResponseConverter converter;

    @Autowired
    public DeckResponseConverter(PlayerResponseConverter playerConverter) {
        this.converter = playerConverter;
    }

    @Override
    public DeckResponse convertBusinessObjectToResponse(DeckInfo businessObject) {
        DeckResponse ret = new DeckResponse();
        ret.setId(businessObject.getDeckId());
        ret.setName(businessObject.getName());
        ret.setOwner(converter.convertBusinessObjectToResponse(businessObject.getPlayer()));
        ret.setBlackCardCount(businessObject.getBlackCardCount());
        ret.setWhiteCardCount(businessObject.getWhiteCardCount());
        return ret;
    }
}
