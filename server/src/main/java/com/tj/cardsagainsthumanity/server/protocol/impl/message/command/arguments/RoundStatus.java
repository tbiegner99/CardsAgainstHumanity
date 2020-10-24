package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public class RoundStatus {
    private Integer id;

    private RoundBlackCard blackCard;
    private String czar;
    private boolean czarIsYou;
    private boolean over;
    private RoundCardPlay winner;
    private boolean allCardsIn;
    private Set<RoundCardPlay> cardsInPlay;
    private Set<RoundCardPlay> revealedCards;

    public RoundStatus() {
    }

    public static RoundStatus fromRound(RoundDriver currentRound) {
        return fromRound(currentRound, null);
    }

    public static RoundStatus fromRound(RoundDriver currentRound, Player currentPlayer) {
        if (currentRound == null) {
            return null;
        }
        RoundStatus roundStatus = new RoundStatus();
        GameRound round = currentRound.getRound();
        boolean currentPlayerIsCzar = currentPlayer != null && currentPlayer.isCzarFor(round);
        if (currentPlayerIsCzar || currentRound.isRoundOver()) {
            roundStatus.setCardsInPlay(getCardsInPlay(round));
        }
        roundStatus.setAllCardsIn(currentRound.areAllCardsIn());
        roundStatus.setOver(currentRound.isRoundOver());
        roundStatus.setCzarIsYou(currentPlayerIsCzar);
        roundStatus.setCzar(round.getCzar().getDisplayName());
        roundStatus.setBlackCard(getBlackCard(round.getBlackCard()));
        roundStatus.setId(round.getId());
        roundStatus.setWinner(getCardInPlay(round.getWinner()));
        return roundStatus;
    }

    private static RoundCardPlay getCardInPlay(CardPlay winner) {
        if (winner == null) {
            return null;
        }
        return RoundCardPlay.fromCardPlay(winner);
    }

    private static Set<RoundCardPlay> getCardsInPlay(GameRound round) {
        return round.getPlays().stream().map(RoundCardPlay::fromCardPlay).collect(Collectors.toSet());
    }

    private static RoundBlackCard getBlackCard(BlackCard blackCard) {
        return new RoundBlackCard(blackCard.getId(), blackCard.getText(), blackCard.getNumberOfAnswers());
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Set<RoundCardPlay> getCardsInPlay() {
        return cardsInPlay;
    }

    public void setCardsInPlay(Set<RoundCardPlay> cardsInPlay) {
        this.cardsInPlay = cardsInPlay;
    }

    public RoundBlackCard getBlackCard() {
        return blackCard;
    }

    public void setBlackCard(RoundBlackCard blackCard) {
        this.blackCard = blackCard;
    }

    public RoundCardPlay getWinner() {
        return winner;
    }

    public void setWinner(RoundCardPlay winner) {
        this.winner = winner;
    }

    public String getCzar() {
        return czar;
    }

    public void setCzar(String czar) {
        this.czar = czar;
    }

    public boolean isCzarIsYou() {
        return czarIsYou;
    }

    public void setCzarIsYou(boolean czarIsYou) {
        this.czarIsYou = czarIsYou;
    }

    public boolean isOver() {
        return over;
    }

    public void setOver(boolean over) {
        this.over = over;
    }

    public boolean isAllCardsIn() {
        return allCardsIn;
    }

    public void setAllCardsIn(boolean allCardsIn) {
        this.allCardsIn = allCardsIn;
    }


    @Override
    public boolean equals(Object o) {
        RoundStatus that = (RoundStatus) o;
        return isCzarIsYou() == that.czarIsYou &&
                isOver() == that.over &&
                isAllCardsIn() == that.allCardsIn &&
                Objects.equals(getId(), that.id) &&
                Objects.equals(getBlackCard(), that.blackCard) &&
                Objects.equals(getCzar(), that.czar) &&
                Objects.equals(getWinner(), that.winner) &&
                Objects.equals(getCardsInPlay(), that.cardsInPlay);
    }

    @Override
    public String toString() {
        return "RoundStatus{" +
                "id=" + id +
                ", blackCard=" + blackCard +
                ", czar='" + czar + '\'' +
                ", czarIsYou=" + czarIsYou +
                ", over=" + over +
                ", winner=" + winner +
                ", allCardsIn=" + allCardsIn +
                ", cardsInPlay=" + cardsInPlay +
                '}';
    }
}
