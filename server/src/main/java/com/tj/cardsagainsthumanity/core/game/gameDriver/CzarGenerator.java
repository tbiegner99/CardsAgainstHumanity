package com.tj.cardsagainsthumanity.core.game.gameDriver;

import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.*;

public class CzarGenerator implements Iterator<Player> {

    private int iterator = 0;

    private List<Player> players;

    public CzarGenerator(Collection<Player> players) {
        this.players = new ArrayList<>(players);
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(this.players);
    }

    @Override
    public boolean hasNext() {
        return !players.isEmpty();
    }

    public List<Player> getCzarOrder() {
        return this.players;
    }

    @Override
    public Player next() {
        Player next = players.get(iterator);
        iterator = ( iterator + 1 ) % players.size();
        return next;
    }

    public void removePlayer(Player player) {
        players.remove(player);
    }

    public void addPlayer(Player player) {
        players.add(player);
    }
}
