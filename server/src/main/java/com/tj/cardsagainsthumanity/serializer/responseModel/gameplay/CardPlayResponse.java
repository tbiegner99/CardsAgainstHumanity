package com.tj.cardsagainsthumanity.serializer.responseModel.gameplay;

import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;

import java.util.List;
import java.util.Objects;

public class CardPlayResponse {
    private Integer id;
    private Integer roundId;
    private PlayerResponse player;
    private List<CardResponse> cards;
    private Boolean winner;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoundId() {
        return roundId;
    }

    public void setRoundId(Integer roundId) {
        this.roundId = roundId;
    }

    public PlayerResponse getPlayer() {
        return player;
    }

    public void setPlayer(PlayerResponse player) {
        this.player = player;
    }

    public List<CardResponse> getCards() {
        return cards;
    }

    public void setCards(List<CardResponse> cards) {
        this.cards = cards;
    }

    public Boolean getWinner() {
        return winner;
    }

    public void setWinner(Boolean winner) {
        this.winner = winner;
    }

    @Override
    public boolean equals(Object o) {
        CardPlayResponse that = (CardPlayResponse) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getRoundId(), that.getRoundId()) &&
                Objects.equals(getPlayer(), that.getPlayer()) &&
                Objects.equals(getCards(), that.getCards()) &&
                Objects.equals(getWinner(), that.getWinner());
    }

}
