package com.tj.cardsagainsthumanity.core.game.handManager;

import java.util.Collection;

public interface DrawStrategy<T> {
    Collection<T> drawCards(int numToDraw);
}
