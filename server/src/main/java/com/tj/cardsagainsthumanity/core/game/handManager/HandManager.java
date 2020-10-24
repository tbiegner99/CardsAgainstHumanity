package com.tj.cardsagainsthumanity.core.game.handManager;

import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public interface HandManager<T> {
    Collection<Player> getPlayers();

    Collection<T> getCardsInHandForPlayer(Player player);

    Map<Player, Set<T>> getAllCardsInHand();

    void addPlayer(Player player);

    void removePlayer(Player player);

    int getHandSizeForPlayer(Player player);

    void removeCardsFromHand(Player player, Collection<T> play);

    Collection<T> clearHandForPlayer(Player player);

    int getMinCardsInHand();

    int getMaxCardsInHand();

    void fillOutHandsForAllPlayers(DrawStrategy<T> strategy);

    void fillOutHandForPlayer(Player player, DrawStrategy<T> strategy);

    void addCardsToHandForPlayer(Player player, Collection<T> drawnCards);
}
