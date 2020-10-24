package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.models.gameplay.Game;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class GameDao {


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
        Query query = entityManager.createNativeQuery("Select * from game where code = :code", Game.class);
        query.setParameter("code", code);
        return (Game) query.getSingleResult();
    }

    public Game getGame(Integer gameId) {
        return entityManager.find(Game.class, gameId);
    }


}
