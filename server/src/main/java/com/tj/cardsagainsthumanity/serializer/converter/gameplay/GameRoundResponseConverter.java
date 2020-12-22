package com.tj.cardsagainsthumanity.serializer.converter.gameplay;

import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.serializer.ResponseConverter;
import com.tj.cardsagainsthumanity.serializer.converter.card.CardResponseConverter;
import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.CardPlayResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.GameRoundResponse;
import com.tj.cardsagainsthumanity.serializer.responseModel.gameplay.PlayerResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.stream.Collectors;

@Component
public class GameRoundResponseConverter implements ResponseConverter<GameRound, GameRoundResponse> {
    private final CardResponseConverter cardResponseConverter;
    private final PlayerResponseConverter playerResponseConverter;
    private final CardPlayResponseConverter cardPlayResponseConverter;

    public GameRoundResponseConverter(@Autowired CardResponseConverter cardResponseConverter, @Autowired PlayerResponseConverter playerResponseConverter, @Autowired CardPlayResponseConverter cardPlayResponseConverter) {
        this.cardResponseConverter = cardResponseConverter;
        this.playerResponseConverter = playerResponseConverter;
        this.cardPlayResponseConverter = cardPlayResponseConverter;
    }

    @Override
    public GameRoundResponse convertBusinessObjectToResponse(GameRound businessObject) {
        Collection<CardPlayResponse> cardPlays = businessObject.getPlays().stream()
                .map(cardPlayResponseConverter::convertBusinessObjectToResponse)
                .collect(Collectors.toList());
        CardResponse blackCardResponse = cardResponseConverter.convertBusinessObjectToResponse(businessObject.getBlackCard());
        PlayerResponse czarResponse = playerResponseConverter.convertBusinessObjectToResponse(businessObject.getCzar());

        GameRoundResponse response = new GameRoundResponse();
        response.setId(businessObject.getId());
        response.setCzar(czarResponse);
        response.setPlays(cardPlays);
        response.setBlackCard(blackCardResponse);
        if (businessObject.getWinner() != null) {
            response.setWinnerId(businessObject.getWinner().getId());
        }

        return response;
    }
}
