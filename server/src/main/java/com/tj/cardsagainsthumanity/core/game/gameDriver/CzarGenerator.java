package com.tj.cardsagainsthumanity.core.game.gameDriver;

import com.tj.cardsagainsthumanity.models.gameplay.Player;

import java.util.*;

public class CzarGenerator implements Iterator<Player> {

    private int iterator = 0;

    private List<Player> players;
    private Map<Integer, Integer> indexMap;

    public CzarGenerator(Collection<Player> players) {
        this.players = new ArrayList<>(players);
        shuffle();
    }

    public void shuffle() {
        Collections.shuffle(this.players);
        computeCzarIndexes();
    }

    private void computeCzarIndexes() {
        indexMap = new HashMap<>();
        int currentIndex = 0;
        for (Player p : players) {
            indexMap.put(p.getId(), currentIndex++);
        }
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
        iterator = (iterator + 1) % players.size();
        return next;
    }

    public Integer getCzarIndexForPlayer(Integer playerId) {
        return indexMap.get(playerId);
    }

    public void removePlayer(Player player) {

        players.remove(player);
        computeCzarIndexes();
    }

    public void addPlayer(Player player) {
        players.add(player);
        computeCzarIndexes();

    }

    public boolean isPlayerInGame(Integer playerId) {
        return indexMap.containsKey(playerId);
    }
}
