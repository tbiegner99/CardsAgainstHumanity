package com.tj.cardsagainsthumanity.models.stats;

import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class CommonCardStats implements CardStats {
    private Integer likes = 0;
    private Integer dislikes = 0;

    @Override
    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    @Override
    public Integer getDislikes() {
        return dislikes;
    }

    public void setDislikes(Integer dislikes) {
        this.dislikes = dislikes;
    }

    @Override
    public void incrementLikes() {
        likes++;
    }

    @Override
    public void incrementDislikes() {
        dislikes++;
    }

}
