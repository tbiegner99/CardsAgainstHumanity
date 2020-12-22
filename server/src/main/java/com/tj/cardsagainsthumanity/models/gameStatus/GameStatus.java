package com.tj.cardsagainsthumanity.models.gameStatus;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import com.tj.cardsagainsthumanity.serializer.responseModel.deck.DeckResponse;

import java.util.List;

public abstract class GameStatus {
    private Integer gameId;
    private Game.GameState state;
    private RoundStatus round;
    private List<PlayerInfo> players;
    private String code;
    private DeckResponse deck;

    public GameStatus(Integer gameId, String code, Game.GameState state, DeckResponse deck, RoundStatus roundStatus, List<PlayerInfo> players) {
        this.gameId = gameId;
        this.state = state;
        this.round = roundStatus;
        this.players = players;
        this.code = code;
        this.deck = deck;
    }

    public String getCode() {
        return code;
    }

    public Integer getGameId() {
        return gameId;
    }

    public Game.GameState getState() {
        return state;
    }

    public RoundStatus getRound() {
        return round;
    }

    public List<PlayerInfo> getPlayers() {
        return players;
    }

    public DeckResponse getDeck() {
        return deck;
    }
}
