package com.tj.cardsagainsthumanity.dao.gameplay;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.List;

@Repository
public class PlayerDao {

    @PersistenceContext
    private EntityManager entityManager;

    public PlayerDao(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Player savePlayer(Player player) {
        return entityManager.merge(player);
    }

    public Player getPlayerByEmail(String email) {
        String query = "SELECT * FROM users where email=:email";
        Query q = entityManager.createNativeQuery(query, Player.class);
        q.setParameter("email", email);
        List results = q.getResultList();
        if (results.isEmpty()) {
            return null;
        }
        return (Player) results.get(0);
    }

    public Player getPlayerById(Integer id) {
        return entityManager.find(Player.class, id);
    }
}
