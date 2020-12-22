package com.tj.cardsagainsthumanity.core.game.handManager;

import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.*;

public class GenericHandManager<T> implements HandManager<T> {
    private final Map<Player, Set<T>> hands;
    private final int maxHandSize;
    private final int minHandSize;

    public GenericHandManager(int handSize) {
        this(new HashMap<>(), handSize);
    }

    public GenericHandManager(Map<Player, Set<T>> hands, int handSize) {
        this(hands, handSize, handSize);
    }

    public GenericHandManager(Map<Player, Set<T>> hands, int maxCards, int minCards) {
        this.hands = hands;
        this.minHandSize = minCards;
        this.maxHandSize = maxCards;
    }

    @Override
    public Set<Player> getPlayers() {
        return hands.keySet();
    }

    @Override
    public Set<T> getCardsInHandForPlayer(Player player) {
        return hands.getOrDefault(player, new HashSet<>());
    }

    @Override
    public Map<Player, Set<T>> getAllCardsInHand() {
        return hands;
    }

    @Override
    public void addPlayer(Player player) {
        hands.putIfAbsent(player, new HashSet<>());
    }

    @Override
    public void removePlayer(Player player) {
        hands.remove(player);
    }

    @Override
    public int getHandSizeForPlayer(Player player) {
        return getCardsInHandForPlayer(player).size();
    }

    @Override
    public void removeCardsFromHand(Player player, Collection<T> play) {
        hands.get(player).removeAll(play);
    }

    @Override
    public Set<T> clearHandForPlayer(Player player) {
        Set<T> hand = getCardsInHandForPlayer(player);
        hands.put(player, new HashSet<>());
        return hand;
    }

    @Override
    public int getMinCardsInHand() {
        return minHandSize;
    }

    @Override
    public int getMaxCardsInHand() {
        return maxHandSize;
    }

    @Override
    public void fillOutHandsForAllPlayers(DrawStrategy<T> strategy) {
        for (Player player : getPlayers()) {
            fillOutHandForPlayer(player, strategy);
        }
    }

    @Override
    public void fillOutHandForPlayer(Player player, DrawStrategy<T> strategy) {
        int handSize = getHandSizeForPlayer(player);

        int cardsToDraw = getMinCardsInHand() - handSize;

        if (cardsToDraw > 0) {
            Collection<T> drawnCards = strategy.drawCards(cardsToDraw);
            addCardsToHandForPlayer(player, drawnCards);
        }
    }

    @Override
    public void addCardsToHandForPlayer(Player player, Collection<T> drawnCards) {
        Set<T> playerHand = getCardsInHandForPlayer(player);
        int newSize = drawnCards.size() + playerHand.size();
        if (newSize > getMaxCardsInHand()) {
            throw new IllegalStateException();
        }
        playerHand.addAll(drawnCards);
        hands.putIfAbsent(player, playerHand);
    }


}
