package com.tj.cardsagainsthumanity.dao;

import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.ParameterMode;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class CardDao {
    @PersistenceContext
    private EntityManager entityManager;

    @Autowired
    public CardDao(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Card saveCard(Card card) {
        if (card.getId() == null) {
            entityManager.persist(card.getPlayStats());
            entityManager.persist(card);
            entityManager.flush();
            return card;
        }
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
       /* String query = "SELECT w.* FROM white_card w\n" +
                "Left join card_play_white_cards pc \n" +
                "   inner join card_play cp on cp.card_play_id= pc.card_play_id\n" +
                "   inner join game_round r on r.game_round_id=cp.game_round_id and r.game_id = :gameId\n" +
                "on pc.white_card_id=w.card_id\n" +
                "left join player_hand h on h.white_card_id = w.card_id and h.game_id = :gameId \n" +
                "WHERE h.game_id is null and r.game_round_id is null\n" +
                "order by rand()\n" +
                "limit :limit";*/
        Query q = entityManager.createStoredProcedureQuery("white_card_deck_for_game")
                .registerStoredProcedureParameter("game", Integer.class, ParameterMode.IN)
                .setParameter("game", gameId);
        List<Integer> results = q.getResultList();
        List<Integer> cardIds = getNRandomElementsFrom(results, numToReturn);
        return cardIds.stream().map(this::getWhiteCard).collect(Collectors.toList());
    }

    private <T> T removeRandomElementFrom(List<T> list) {
        int randomIndex = (int) (Math.random() * list.size());
        return list.remove(randomIndex);
    }

    private <T> T getRandomElementFrom(List<T> list) {
        int randomIndex = (int) (Math.random() * list.size());
        return list.get(randomIndex);
    }

    private <T> List<T> getNRandomElementsFrom(List<T> results, Integer numToReturn) {
        List<T> copy = new ArrayList<>(results);
        List<T> ret = new ArrayList<>();
        for (int i = 0; i < numToReturn; i++) {
            ret.add(removeRandomElementFrom(copy));
        }
        return ret;
    }

    public List<WhiteCard> getRandomWhiteCards(Integer numToReturn) {
        String query = "SELECT * FROM white_card where deleted=0 ORDER BY RAND() LIMIT :limit";
        Query q = entityManager.createNativeQuery(query, WhiteCard.class);
        q.setParameter("limit", numToReturn);
        return q.getResultList();
    }

    public BlackCard getBlackCard(Integer cardId) {
        return entityManager.find(BlackCard.class, cardId);
    }

    public BlackCard getBlackCard(Integer packageId, Integer cardId) {
        String query = "SELECT * FROM black_card  where deleted=0 and package_id=:packageId and card_id=:cardId";
        Query q = entityManager.createNativeQuery(query, BlackCard.class);
        q.setParameter("packageId", packageId);
        q.setParameter("cardId", cardId);
        return (BlackCard) q.getSingleResult();
    }

    public WhiteCard getWhiteCard(Integer packageId, Integer cardId) {
        String query = "SELECT * FROM white_card  where deleted=0 and package_id=:packageId and card_id=:cardId";
        Query q = entityManager.createNativeQuery(query, WhiteCard.class);
        q.setParameter("packageId", packageId);
        q.setParameter("cardId", cardId);
        return (WhiteCard) q.getSingleResult();
    }


    public BlackCard getRandomBlackCard() {
        String query = "SELECT * FROM black_card where deleted=0 ORDER BY RAND() LIMIT :limit";
        Query q = entityManager.createNativeQuery(query, BlackCard.class);
        q.setParameter("limit", 1);
        return (BlackCard) q.getSingleResult();
    }

    public BlackCard getRandomBlackCardForGame(Integer gameId) {
        Query q = entityManager.createStoredProcedureQuery("black_card_deck_for_game")
                .registerStoredProcedureParameter("game", Integer.class, ParameterMode.IN)
                .setParameter("game", gameId);
        List<Integer> results = q.getResultList();
        Integer cardId = getRandomElementFrom(results);
        return this.getBlackCard(cardId);
    }

    public List<BlackCard> getBlackCardsForPackage(Integer packageId) {
        String query = "SELECT * FROM black_card  where deleted=0 and package_id=:packageId";
        Query q = entityManager.createNativeQuery(query, BlackCard.class);
        q.setParameter("packageId", packageId);
        return q.getResultList();
    }

    public List<WhiteCard> getWhiteCardsForPackage(Integer packageId) {
        String query = "SELECT * FROM white_card where  deleted=0 and package_id=:packageId";
        Query q = entityManager.createNativeQuery(query, WhiteCard.class);
        q.setParameter("packageId", packageId);
        return q.getResultList();
    }

    public void refresh(Card savedCard) {
        entityManager.refresh(savedCard);
    }
}