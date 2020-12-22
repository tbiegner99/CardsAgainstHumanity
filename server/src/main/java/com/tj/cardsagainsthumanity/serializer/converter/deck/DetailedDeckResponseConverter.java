package com.tj.cardsagainsthumanity.serializer.converter.deck;

import com.tj.cardsagainsthumanity.models.cards.DeckCards;
import com.tj.cardsagainsthumanity.serializer.ResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CardResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DeckResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DetailedDeckResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DetailedDeckResponseConverter implements ResponseConverter<DeckCards, DetailedDeckResponse> {

    private final DeckResponseConverter deckConverter;
    private final CardResponseConverter cardConverter;

    @Autowired
    public DetailedDeckResponseConverter(DeckResponseConverter deckConverter, CardResponseConverter cardConverter) {
        this.deckConverter = deckConverter;
        this.cardConverter = cardConverter;
    }


    @Override
    public DetailedDeckResponse convertBusinessObjectToResponse(DeckCards businessObject) {
        DeckResponse base = this.deckConverter.convertBusinessObjectToResponse(businessObject);
        DetailedDeckResponse ret = new DetailedDeckResponse(base);
        List<Integer> whiteCards = businessObject.getWhiteCards();
        List<Integer> blackCards = businessObject.getBlackCards();
        ret.setBlackCards(blackCards);
        ret.setWhiteCards(whiteCards);
        return ret;
    }
}
