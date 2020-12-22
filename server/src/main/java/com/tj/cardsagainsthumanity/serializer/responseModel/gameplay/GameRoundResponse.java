package com.tj.cardsagainsthumanity.serializer.responseModel.gameplay;

import com.tj.cardsagainsthumanity.serializer.responseModel.cardPackage.CardResponse;

import java.util.Collection;
import java.util.Objects;

public class GameRoundResponse {
    private Integer id;
    private CardResponse blackCard;
    private PlayerResponse czar;
    private Collection<CardPlayResponse> plays;
    private Integer winnerId;

    public Integer getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Integer winnerId) {
        this.winnerId = winnerId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public CardResponse getBlackCard() {
        return blackCard;
    }

    public void setBlackCard(CardResponse blackCard) {
        this.blackCard = blackCard;
    }

    public PlayerResponse getCzar() {
        return czar;
    }

    public void setCzar(PlayerResponse czar) {
        this.czar = czar;
    }

    public Collection<CardPlayResponse> getPlays() {
        return plays;
    }

    public void setPlays(Collection<CardPlayResponse> plays) {
        this.plays = plays;
    }


    @Override
    public boolean equals(Object o) {
        GameRoundResponse that = (GameRoundResponse) o;
        return Objects.equals(getId(), that.getId()) &&
                Objects.equals(getBlackCard(), that.getBlackCard()) &&
                Objects.equals(getCzar(), that.getCzar()) &&
                Objects.equals(getPlays(), that.getPlays()) &&
                Objects.equals(getWinnerId(), that.getWinnerId());
    }

}
