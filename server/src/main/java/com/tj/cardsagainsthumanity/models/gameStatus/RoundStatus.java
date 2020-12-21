package com.tj.cardsagainsthumanity.models.gameStatus;

import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundBlackCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.WinningPlay;

import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

public class RoundStatus {
    private Integer id;
    private RoundBlackCard blackCard;
    private Integer numberOfPlays;
    private Integer waitingForPlays;
    private boolean judgementHasStarted;
    private PlayerInfo czar;
    private boolean czarIsYou;
    private boolean isRoundOver;
    private WinningPlay winner;
    private boolean allCardsIn;
    private Set<RoundCardPlay> revealedPlays;
    private boolean canPlayCard;
    private RoundCardPlay myPlay;

    public RoundStatus(Integer id, RoundBlackCard blackCard, Integer numberOfPlays, Integer waitingForPlays, boolean judgementHasStarted, PlayerInfo czar, boolean czarIsYou, boolean isRoundOver, WinningPlay winner, boolean allCardsIn, Set<RoundCardPlay> revealedPlays) {
        this.id = id;
        this.blackCard = blackCard;
        this.numberOfPlays = numberOfPlays;
        this.waitingForPlays = waitingForPlays;
        this.judgementHasStarted = judgementHasStarted;
        this.czar = czar;
        this.czarIsYou = czarIsYou;
        this.isRoundOver = isRoundOver;
        this.winner = winner;
        this.allCardsIn = allCardsIn;
        this.revealedPlays = revealedPlays;
    }

    private RoundStatus() {
    }

    public static RoundStatus fromRound(RoundDriver currentRound) {
        return fromRound(currentRound, null);
    }

    private static boolean canPlayCard(RoundDriver round, Player player) {
        if (player == null) {
            return false;
        }
        Integer playerId = player.getId();
        boolean isMemberOfGame = round.getGame().isPlayerInGame(player);
        boolean hasPlayedCard = round.getAllCardPlays().stream().filter(play -> play.getPlayer().getId().equals(playerId)).findFirst().isPresent();
        return player != null && isMemberOfGame && !hasPlayedCard;
    }

    public static RoundStatus fromRound(RoundDriver currentRound, Player currentPlayer) {
        if (currentRound == null) {
            return null;
        }
        RoundStatus roundStatus = new RoundStatus();
        GameRound round = currentRound.getRound();
        boolean currentPlayerIsCzar = currentPlayer != null && currentPlayer.getId().equals(round.getCzarId());
        roundStatus.id = round.getId();
        roundStatus.blackCard = getBlackCard(round.getBlackCard());
        roundStatus.numberOfPlays = round.getPlays().size();
        roundStatus.waitingForPlays = round.getGame().getNumberOfPlayers() - roundStatus.numberOfPlays - 1;
        roundStatus.allCardsIn = roundStatus.isRoundOver || roundStatus.waitingForPlays == 0;
        roundStatus.isRoundOver = currentRound.isRoundOver();
        roundStatus.czarIsYou = currentPlayerIsCzar;
        roundStatus.czar = PlayerInfo.fromPlayer(currentRound.getGame(), round.getCzar());
        roundStatus.winner = getCardInPlay(round.getWinner());
        roundStatus.revealedPlays = getRevealedCards(round);
        roundStatus.canPlayCard = canPlayCard(currentRound, currentPlayer);
        roundStatus.myPlay = getMyPlay(currentRound, currentPlayer);

        return roundStatus;
    }

    private static RoundCardPlay getMyPlay(RoundDriver currentRound, Player currentPlayer) {
        if (currentPlayer == null) {
            return null;
        }
        Optional<CardPlay> play = currentRound.getPlayForPlayer(currentPlayer);
        return play.map(RoundCardPlay::fromCardPlay).orElse(null);
    }

    private static WinningPlay getCardInPlay(CardPlay winner) {
        if (winner == null) {
            return null;
        }
        return WinningPlay.fromCardPlay(winner);
    }

    private static Set<RoundCardPlay> getRevealedCards(GameRound round) {
        return round.getPlays()
                .stream()
                .filter(play -> play.isRevealed())
                .map(RoundCardPlay::fromCardPlay)
                .collect(Collectors.toSet());
    }

    private static RoundBlackCard getBlackCard(BlackCard blackCard) {
        return new RoundBlackCard(blackCard.getId(), blackCard.getText(), blackCard.getNumberOfAnswers());
    }

    public Integer getId() {
        return id;
    }

    public RoundBlackCard getBlackCard() {
        return blackCard;
    }

    public Integer getNumberOfPlays() {
        return numberOfPlays;
    }

    public Integer getWaitingForPlays() {
        return waitingForPlays;
    }

    public boolean isJudgementHasStarted() {
        return judgementHasStarted;
    }

    public PlayerInfo getCzar() {
        return czar;
    }

    public boolean isCzarIsYou() {
        return czarIsYou;
    }

    public boolean isRoundOver() {
        return isRoundOver;
    }

    public WinningPlay getWinner() {
        return winner;
    }

    public boolean isAllCardsIn() {
        return allCardsIn;
    }

    public boolean getCanPlayCard() {
        return canPlayCard;
    }

    public Set<RoundCardPlay> getRevealedPlays() {
        return revealedPlays;
    }


    public RoundCardPlay getMyPlay() {
        return myPlay;
    }
}
