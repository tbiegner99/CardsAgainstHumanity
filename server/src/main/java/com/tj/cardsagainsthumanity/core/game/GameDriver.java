package com.tj.cardsagainsthumanity.core.game;

import com.tj.cardsagainsthumanity.core.game.events.dispatcher.GameEventRegistry;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.models.gameplay.game.Scoreboard;

import java.util.Collection;
import java.util.List;

public interface GameDriver extends GameEventRegistry {

    void start();

    void end();

    void pause();

    void resume();

    void save();

    Game getGame();

    RoundDriver getCurrentRound();

    RoundDriver nextRound();

    RoundDriver nextRound(RoundType type);

    Scoreboard getScore();

    void addPlayer(Player player);

    void addDisplay();

    void addAudienceMember();

    void removePlayer(Player player);

    List<Player> getPlayers();

    List<Player> getCzarOrder();

    boolean isPlayerInGame(Player player);

    Integer getCzarPositionFor(Integer playerId);

    void onRoundOver(RoundDriver round);

    Integer getGameId();

    String getCode();

    Game.GameState getState();

    default Integer getNumberOfPlayers() {
        return getPlayers().size();
    }

    void replacePlayedCards(Collection<CardPlay> allCardPlays);
}
