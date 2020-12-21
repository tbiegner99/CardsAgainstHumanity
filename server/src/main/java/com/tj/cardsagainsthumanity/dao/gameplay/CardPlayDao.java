package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.exceptions.NoRowUpdatedException;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class CardPlayDao {

    @PersistenceContext
    private EntityManager entityManager;

    public CardPlayDao(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CardPlay saveCardPlay(CardPlay cardPlay) {
        return entityManager.merge(cardPlay);
    }

    public void makeCardPlayWinner(Integer cardPlayId) {
        Query query = entityManager.createNativeQuery("UPDATE card_play set winner = 1 where card_play_id =:playId");
        query.setParameter("playId", cardPlayId);
        int rowsUpdated = query.executeUpdate();
        if (rowsUpdated != 1) {
            throw new NoRowUpdatedException("There is no single card play with id: " + cardPlayId + ". Rows updated::" + rowsUpdated);
        }
    }
}
