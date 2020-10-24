package com.tj.cardsagainsthumanity.models.cards;

import com.tj.cardsagainsthumanity.models.stats.CardStats;

public interface Card {
    Integer getId();

    String getText();

    CardPackage getPackage();

    void setPackage(CardPackage pack);

    CardStats getPlayStats();

    Boolean isDeleted();

    void setDeleted(Boolean deleted);

    void incrementWins();

    void incrementLikes();
}
