package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Repository
public class GameDao {


    @PersistenceContext
    private EntityManager entityManager;


    @Autowired
    public GameDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public void saveGame(Game game) {
        if (game.getId() == null) {
            entityManager.persist(game);
        } else {
            entityManager.merge(game);
        }
        entityManager.flush();
    }

    public void deleteGame(Game game) {
        game.setDeleted(true);
        this.saveGame(game);
    }


    public Game getGameByCode(String code) {
        Query query = entityManager.createNativeQuery("Select game_id from game where code = :code");
        query.setParameter("code", code);
        Integer gameId = (Integer) query.getSingleResult();
        return this.getGame(gameId);
    }

    public Game getGame(Integer gameId) {
        return entityManager.find(Game.class, gameId);
    }


}
