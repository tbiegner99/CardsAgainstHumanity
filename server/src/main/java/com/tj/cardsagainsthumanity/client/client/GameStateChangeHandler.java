package com.tj.cardsagainsthumanity.client.client;

import com.tj.cardsagainsthumanity.models.gameStatus.GameStatus;
import com.tj.cardsagainsthumanity.models.gameStatus.RoundStatus;

public interface GameStateChangeHandler {
    void onGameStateChanged(GameStatus gameStatus);

    void onRoundStateChanged(RoundStatus roundStatus);
}
