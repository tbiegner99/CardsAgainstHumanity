package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.exceptions.NoRowUpdatedException;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class GameRoundDao {

    private EntityManager entityManager;

    public GameRoundDao(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public GameRound saveGameRound(GameRound round) {
        GameRound ret = round;
        if (round.getId() == null) {
            entityManager.persist(round);
        } else {
            ret = entityManager.merge(round);
        }
        entityManager.flush();
        return ret;
    }

    public GameRound getGameRound(Integer roundId) {
        return entityManager.find(GameRound.class, roundId);
    }

    public void setWinningPlayForRound(Integer gameRoundId, Integer cardPlayId) {
        Query query = entityManager.createNativeQuery("UPDATE game_round round, card_play play \n" +
                "SET round.winning_play_id = :playId\n" +
                "WHERE round.winning_play_id is null and round.game_round_id=play.game_round_id \n" +
                "and round.game_round_id=:roundId and play.card_play_id=:playId");
        query.setParameter("roundId", gameRoundId);
        query.setParameter("playId", cardPlayId);
        int rowsUpdated = query.executeUpdate();
        if (rowsUpdated != 1) {
            throw new NoRowUpdatedException("Expected a single row to be updated. Rows updated: " + rowsUpdated);
        }
    }
}
