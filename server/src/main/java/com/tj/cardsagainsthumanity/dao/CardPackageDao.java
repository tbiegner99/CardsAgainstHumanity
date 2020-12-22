package com.tj.cardsagainsthumanity.dao;

import com.tj.cardsagainsthumanity.exceptions.ConflictException;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;
import java.util.List;

@Repository
public class CardPackageDao {
    @PersistenceContext
    private EntityManager entityManager;

    public CardPackageDao(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CardPackage getCardPackageById(Integer packageId) {
        return entityManager.find(CardPackage.class, packageId);
    }

    public CardPackage getCardPackageByName(String name) {
        String query = "SELECT * FROM card_package where name=:name and deleted=0";
        Query q = entityManager.createNativeQuery(query, CardPackage.class);
        q.setParameter("name", name);
        return (CardPackage) q.getSingleResult();
    }


    public CardPackage saveCardPackage(CardPackage cardPackage) {
        try {
            CardPackage ret = entityManager.merge(cardPackage);
            entityManager.flush();
            return ret;
        } catch (ConstraintViolationException e) {
            throw new ConflictException("The package already exists", e);
        }
    }


    public CardPackage deleteCardPackage(CardPackage cardPackage) {
        cardPackage.setDeleted(true);
        return saveCardPackage(cardPackage);
    }


    public CardPackage saveOrGetExisting(CardPackage cardPackage) {
        try {
            return saveCardPackage(cardPackage);
        } catch (ConflictException e) {
            return getCardPackageByName(cardPackage.getName());
        }
    }

    public List<CardPackage> getPackagesForPlayer(Integer ownerId) {
        String query = "SELECT * FROM card_package where owner_id=:ownerId and deleted=0";
        Query q = entityManager.createNativeQuery(query, CardPackage.class);
        q.setParameter("ownerId", ownerId);
        return q.getResultList();
    }

    public List<CardPackage> getUsablePackagesForPlayer(Integer ownerId) {
        String query = "SELECT * FROM card_package where (owner_id=:ownerId or owner_id is null) and deleted=0";
        Query q = entityManager.createNativeQuery(query, CardPackage.class);
        q.setParameter("ownerId", ownerId);
        return q.getResultList();
    }

    private void updateWhiteCardCountForPackage(Integer id) {
        String query = "Update card_package c set white_card_count=(Select count(*) from white_card where package_id=c.package_id and deleted=0) where package_id=:packageId";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("packageId", id);
        q.executeUpdate();
    }

    private void updateBlackCardCountForPackage(Integer id) {
        String query = "Update card_package c set black_card_count=(Select count(*) from black_card where package_id=c.package_id and deleted=0) where package_id=:packageId";
        Query q = entityManager.createNativeQuery(query);
        q.setParameter("packageId", id);
        q.executeUpdate();
    }

    public void updateCardCountForPackage(Integer id) {
        updateBlackCardCountForPackage(id);
        updateWhiteCardCountForPackage(id);
    }
}
