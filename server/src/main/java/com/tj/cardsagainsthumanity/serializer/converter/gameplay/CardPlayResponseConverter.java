package com.tj.cardsagainsthumanity.serializer.converter.gameplay;

import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.serializer.ResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CardResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.CardPlayResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

import static java.util.stream.Collectors.toList;

@Component
public class CardPlayResponseConverter implements ResponseConverter<CardPlay, CardPlayResponse> {

    private final CardResponseConverter cardConverter;
    private final PlayerResponseConverter playerConverter;

    public CardPlayResponseConverter(@Autowired PlayerResponseConverter playerConverter, @Autowired CardResponseConverter cardConverter) {
        this.playerConverter = playerConverter;
        this.cardConverter = cardConverter;
    }

    @Override
    public CardPlayResponse convertBusinessObjectToResponse(CardPlay businessObject) {
        CardPlayResponse response = new CardPlayResponse();
        List<CardResponse> cardResponses = businessObject.getCards().stream()
                .map(cardConverter::convertBusinessObjectToResponse)
                .collect(toList());
        response.setId(businessObject.getId());
        response.setRoundId(businessObject.getRound().getId());
        if (businessObject.getPlayer() != null) {
            PlayerResponse player = playerConverter.convertBusinessObjectToResponse(businessObject.getPlayer());
            response.setPlayer(player);
        }
        response.setCards(cardResponses);
        response.setWinner(businessObject.getWinner());
        return response;
    }
}
