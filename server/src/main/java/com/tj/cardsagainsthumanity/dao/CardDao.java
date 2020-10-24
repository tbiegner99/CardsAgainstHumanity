package com.tj.cardsagainsthumanity.dao;

import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class CardDao {
    private EntityManager entityManager;

    @Autowired
    public CardDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Card saveCard(Card card) {
        return entityManager.merge(card);
    }

    public Card deleteCard(Card card) {
        card.setDeleted(true);
        return this.saveCard(card);
    }

    public WhiteCard getWhiteCard(Integer cardId) {
        return entityManager.find(WhiteCard.class, cardId);
    }

    public List<WhiteCard> getRandomWhiteCardsForGame(Integer gameId, Integer numToReturn) {
        String query = "SELECT w.* FROM white_card w\n" +
                "Left join card_play_white_cards pc \n" +
                "   inner join card_play cp on cp.card_play_id= pc.card_play_id\n" +
                "   inner join game_round r on r.game_round_id=cp.game_round_id and r.game_id = :gameId\n" +
                "on pc.white_card_id=w.card_id\n" +
                "left join player_hand h on h.white_card_id = w.card_id and h.game_id = :gameId \n" +
                "WHERE h.game_id is null and r.game_round_id is null\n" +
                "order by rand()\n" +
                "limit :limit";
        Query q = entityManager.createNativeQuery(query, WhiteCard.class);
        q.setParameter("gameId", gameId);
        q.setParameter("limit", numToReturn);
        return q.getResultList();
    }

    public List<WhiteCard> getRandomWhiteCards(Integer numToReturn) {
        String query = "SELECT * FROM white_card ORDER BY RAND() LIMIT :limit";
        Query q = entityManager.createNativeQuery(query, WhiteCard.class);
        q.setParameter("limit", numToReturn);
        return q.getResultList();
    }

    public BlackCard getBlackCard(Integer cardId) {
        return entityManager.find(BlackCard.class, cardId);
    }


    public BlackCard getRandomBlackCard() {
        String query = "SELECT * FROM black_card ORDER BY RAND() LIMIT :limit";
        Query q = entityManager.createNativeQuery(query, BlackCard.class);
        q.setParameter("limit", 1);
        return (BlackCard) q.getSingleResult();
    }

    public BlackCard getRandomBlackCardForGame(Integer gameId) {
        String query = "SELECT b.* FROM black_card b\n" +
                "Left join game_round r on r.black_card_id=b.card_id and r.game_id = :gameId\n" +
                "WHERE r.game_id is null and r.game_round_id is null\n" +
                "order by rand()\n" +
                "limit :limit";
        Query q = entityManager.createNativeQuery(query, BlackCard.class);
        q.setParameter("gameId", gameId);
        q.setParameter("limit", 1);
        return (BlackCard) q.getSingleResult();
    }
}