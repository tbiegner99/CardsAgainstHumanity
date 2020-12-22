package com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments;

import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;

import java.util.stream.Collectors;

public class WinningPlay extends RoundCardPlay {

    private String playerName;
    private Integer playerId;

    public WinningPlay() {
    }

    public static WinningPlay fromCardPlay(CardPlay cardPlay) {
        WinningPlay ret = new WinningPlay();
        ret.id = cardPlay.getId();
        ret.cards = cardPlay.getCards().stream().map(card -> new RoundWhiteCard(card.getId(), card.getText())).collect(Collectors.toList());
        ret.playerName = cardPlay.getPlayer().getDisplayName();
        ret.playerId = cardPlay.getPlayer().getId();

        return ret;
    }

    public String getPlayerName() {
        return playerName;
    }

    public Integer getPlayerId() {
        return playerId;
    }
}
