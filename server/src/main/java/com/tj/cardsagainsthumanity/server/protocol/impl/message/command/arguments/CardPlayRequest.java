package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

import java.util.List;

public class CardPlayRequest {
    private Integer roundId;
    private List<RoundWhiteCard> cardsToPlay;

    public CardPlayRequest() {
    }

    public CardPlayRequest(Integer roundId, List<RoundWhiteCard> cardsToPlay) {
        this.roundId = roundId;
        this.cardsToPlay = cardsToPlay;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public List<RoundWhiteCard> getCardsToPlay() {
        return cardsToPlay;
    }

    public void setCardsToPlay(List<RoundWhiteCard> cardsToPlay) {
        this.cardsToPlay = cardsToPlay;
    }
}
