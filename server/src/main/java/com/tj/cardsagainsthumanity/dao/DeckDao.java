package com.tj.cardsagainsthumanity.dao;

import com.tj.cardsagainsthumanity.models.cards.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Repository
public class DeckDao {

    @PersistenceContext
    private EntityManager entityManager;

    public DeckDao(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<DeckEntry> loadEntriesForDeck(Integer deckId) {
        String query = "SELECT * FROM deck_entry d where deck_id=:deckId and deleted=0";
        Query q = entityManager.createNativeQuery(query, DeckEntry.class);
        q.setParameter("deckId", deckId);
        return q.getResultList();
    }

    public List<DeckInfo> getPlayableDecksForPlayer(Integer playerId) {
        String query = "SELECT distinct d.* FROM deck d left join deck_user du on d.deck_id=du.deck_id and du.user_id=:userId where d.user_id is null or d.user_id=:userId or du.user_id=:userId";
        Query q = entityManager.createNativeQuery(query, DeckInfo.class);
        q.setParameter("userId", playerId);
        return q.getResultList();
    }

    public List<DeckInfo> getEditableDecksForPlayer(Integer playerId) {
        String query = "SELECT distinct d.* FROM deck d left join deck_user du on d.deck_id=du.deck_id and du.user_id=:userId where d.user_id=:userId or du.user_id=:userId";
        Query q = entityManager.createNativeQuery(query, DeckInfo.class);
        q.setParameter("userId", playerId);
        return q.getResultList();
    }

    public DeckInfo createDeck(DeckInfo deck) {
        String query = "Insert Into deck (user_id,name) VALUES(:userId,:name)";
        Query insert = entityManager.createNativeQuery(query);
        insert.setParameter("userId", deck.getPlayer() == null ? null : deck.getPlayer().getId());
        insert.setParameter("name", deck.getName());
        insert.executeUpdate();
        Query lastId = entityManager.createNativeQuery("Select LAST_INSERT_ID()");
        BigInteger id = (BigInteger) lastId.getSingleResult();
        deck.setDeckId(id.intValue());
        deck.setCreated(new Date());
        return deck;
    }

    public DeckInfo loadDeckInfo(Integer deckId) {
        String query = "SELECT * FROM deck d where d.deck_id = :deckId";
        Query q = entityManager.createNativeQuery(query, DeckInfo.class);
        q.setParameter("deckId", deckId);

        return (DeckInfo) q.getSingleResult();
    }

    public DeckCards getDeckCards(Integer deckId) {
        DeckInfo deckInfo = loadDeckInfo(deckId);
        List<Integer> whiteCards = loadWhiteCards(deckId);
        List<Integer> blackCards = loadBlackCards(deckId);
        return new DeckCards(deckInfo, whiteCards, blackCards);
    }

    private List<Integer> loadBlackCards(Integer deckId) {
        Query q = entityManager.createStoredProcedureQuery("black_card_deck")
                .registerStoredProcedureParameter("deck", Integer.class, ParameterMode.IN)
                .setParameter("deck", deckId);
        return getIdList(q.getResultList());
    }

    private List<Integer> getIdList(List<Object> resultList) {
        return resultList.stream().map(item -> (Integer) item).collect(Collectors.toList());
    }


    private List<Integer> loadWhiteCards(Integer deckId) {
        Query q = entityManager.createStoredProcedureQuery("white_card_deck")
                .registerStoredProcedureParameter("deck", Integer.class, ParameterMode.IN)
                .setParameter("deck", deckId);
        return getIdList(q.getResultList());
    }

    public void removeDeckEntry(Integer entryId) {
        String query = "DELETE FROM deck_entry where entryId = :entryId";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("entryId", entryId);
        q.executeUpdate();
    }

    public void removeAllDeckEntries(Integer deckId) {
        String query = "DELETE FROM deck_entry where deck_id = :deckId";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("deckId", deckId);
        q.executeUpdate();
    }

    public void deleteDeck(Integer deckId) {
        String query = "DELETE FROM deck where deck_id = :deckId";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("deckId", deckId);
        q.executeUpdate();
        removeAllDeckEntries(deckId);
    }

    public void removeCard(Integer deckId, Integer packageId, Integer cardId, CardType type) {
        String query = "INSERT INTO deck_entry (deck_id,package_id,card_id,card_type,exclude) VALUES (:deckId,:packageId,:cardId,:cardType,1) ON DUPLICATE KEY UPDATE exclude=1";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("deckId", deckId);
        q.setParameter("packageId", packageId);
        q.setParameter("cardId", cardId);
        q.setParameter("cardType", type.getType());
        q.executeUpdate();
        this.updateCardCount(deckId);
    }

    public List<Integer> getDecksThatUsePackage(Integer packageId) {
        String query = "SELECT distinct d.deck_id FROM deck d inner join deck_entry e on d.deck_id=e.deck_id  where e.deleted=0  and e.card_id is null and e.package_id=:packageId and exclude=0";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("packageId", packageId);
        return getIdList(q.getResultList());
    }

    public void updateCardCountForDecksThatUsePackage(Integer packageId) {
        List<Integer> decksThatUsePackage = getDecksThatUsePackage(packageId);
        for (Integer deckId : decksThatUsePackage) {
            updateCardCount(deckId);
        }

    }

    private void updateCardCount(Integer deckId) {
        entityManager.createStoredProcedureQuery("update_card_count", WhiteCard.class)
                .registerStoredProcedureParameter("deck_id", Integer.class, ParameterMode.IN)
                .setParameter("deck_id", deckId)
                .execute();
    }

    public void deletePackage(Integer deckId, Integer packageId) {
        String query = "DELETE from deck_entry where deck_id=:deckId and package_id=:packageId";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("deckId", deckId);
        q.setParameter("packageId", packageId);
        q.executeUpdate();
        this.updateCardCount(deckId);
    }

    public void addCard(Integer deckId, Integer packageId, Integer cardId, CardType type) {
        String query = "INSERT INTO deck_entry (deck_id,package_id, card_id,card_type) VALUES (:deckId,:packageId,:cardId,:cardType) ON DUPLICATE KEY UPDATE exclude=0";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("deckId", deckId);
        q.setParameter("cardId", cardId);
        q.setParameter("packageId", packageId);
        q.setParameter("cardType", type.getType());
        System.out.println("Rows Updated" + q.executeUpdate());
        this.updateCardCount(deckId);
    }

    private void deleteCardEntriesForPackage(Integer deckId, Integer packageId) {
        String query = "DELETE from deck_entry where deck_id=:deckId and package_id=:packageId and card_id is not null and exclude=0";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("deckId", deckId);
        q.setParameter("packageId", packageId);
        q.executeUpdate();
    }

    public void addPackage(Integer deckId, Integer packageId) {
        String query = "INSERT INTO deck_entry (deck_id,package_id) VALUES (:deckId,:packageId) ON DUPLICATE KEY UPDATE exclude=0";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("deckId", deckId);
        q.setParameter("packageId", packageId);
        q.executeUpdate();
        this.deleteCardEntriesForPackage(deckId, packageId);
        this.updateCardCount(deckId);
    }


    public boolean userOwns(Integer playerId, Integer deckId) {
        String query = "SELECT  d.* FROM deck d left join deck_user du on d.deck_id=du.deck_id and du.user_id=:userId where d.deck_id=:deckId and (d.user_id=:userId or du.user_id=:userId)";
        Query q = entityManager.createNativeQuery(query, DeckInfo.class);
        q.setParameter("userId", playerId);
        q.setParameter("deckId", deckId);
        try {
            Object result = q.getSingleResult();
            return result != null;
        } catch (NoResultException e) {
            return false;
        }
    }

    public boolean userHasReadAccess(Integer playerId, Integer deckId) {
        String query = "SELECT  d.*  FROM deck d left join deck_user du on d.deck_id=du.deck_id and du.user_id=:userId where d.deck_id=:deckId and (d.user_id is null ord.user_id=:userId or du.user_id=:userId)";
        Query q = entityManager.createNativeQuery(query, DeckInfo.class);
        q.setParameter("userId", playerId);
        q.setParameter("deck_id", deckId);
        try {
            Object result = q.getSingleResult();
            return result != null;
        } catch (NoResultException e) {
            return false;
        }
    }
}
