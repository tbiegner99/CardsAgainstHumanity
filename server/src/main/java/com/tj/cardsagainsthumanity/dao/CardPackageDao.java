package com.tj.cardsagainsthumanity.dao;

import com.tj.cardsagainsthumanity.exceptions.ConflictException;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

@Repository
public class CardPackageDao {
    private EntityManager entityManager;

    public CardPackageDao(@Autowired EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public CardPackage getCardPackageById(Integer packageId) {
        return entityManager.find(CardPackage.class, packageId);
    }

    public CardPackage getCardPackageByName(String name) {
        String query = "SELECT * FROM card_package where name=:name";
        Query q = entityManager.createNativeQuery(query, CardPackage.class);
        q.setParameter("name", name);
        return (CardPackage) q.getSingleResult();
    }


    public CardPackage saveCardPackage(CardPackage cardPackage) {
        try {
            return entityManager.merge(cardPackage);
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
}
