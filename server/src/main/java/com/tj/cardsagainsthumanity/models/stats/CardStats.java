package com.tj.cardsagainsthumanity.models.stats;

public interface CardStats {
    Integer getWins();

    Integer getLikes();

    Integer getDislikes();

    void incrementWins();

    void incrementLikes();

    void incrementDislikes();
}
