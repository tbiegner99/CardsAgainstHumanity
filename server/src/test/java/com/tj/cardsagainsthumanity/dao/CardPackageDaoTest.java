package com.tj.cardsagainsthumanity.dao;

import com.tj.cardsagainsthumanity.exceptions.ConflictException;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.validation.ConstraintViolationException;

import static org.junit.Assert.*;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

public class CardPackageDaoTest {
    @Mock
    private EntityManager em;

    @Mock
    private CardPackage expectedResult;

    @Mock
    private CardPackage testCardPackage;

    @Mock
    private CardPackage mockQueryResponse;

    @Mock
    private Query createdQuery;

    @Mock
    private ConstraintViolationException constraintError;


    private CardPackageDao packageDao;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        packageDao = new CardPackageDao(em);
        when(em.merge(any())).thenReturn(expectedResult);
        when(em.find(CardPackage.class, 4)).thenReturn(expectedResult);
        when(em.createNativeQuery(any(), eq(CardPackage.class))).thenReturn(createdQuery);
        when(createdQuery.getSingleResult()).thenReturn(mockQueryResponse);
    }

    @Test
    public void deleteCardPackage() {
        CardPackage result = packageDao.deleteCardPackage(testCardPackage);
        // it sets the soft delete flag on the object
        verify(testCardPackage, times(1)).setDeleted(true);
        //it saves the object;
        verify(em, times(1)).merge(testCardPackage);
        //it returns the result of the save
        assertSame(result, expectedResult);
    }

    @Test
    public void getCardPackageById() {
        CardPackage result = packageDao.getCardPackageById(4);
        //it fetches from the entity manager;
        verify(em, times(1)).find(CardPackage.class, 4);
        //returns the result
        assertSame(result, expectedResult);
    }

    @Test
    public void saveCardPackage() {
        CardPackage result = packageDao.saveCardPackage(testCardPackage);
        //it calls the entity manager to persiste the card
        verify(em, times(1)).merge(testCardPackage);

        //it returns the expectedresult
        assertSame(result, expectedResult);
    }

    @Test
    public void saveCardPackage_throwsConflictError() {
        when(em.merge(testCardPackage)).thenThrow(constraintError);
        try {
            packageDao.saveCardPackage(testCardPackage);
        } catch (ConflictException e) {
            return;
        }
        fail("Expected conflict error");
    }

    @Test
    public void saveOrGetExisting_whenNotExists() {
        CardPackage pack = packageDao.saveOrGetExisting(testCardPackage);
        assertSame(pack, expectedResult);
        assertNotSame(pack, mockQueryResponse);
    }

    @Test
    public void saveOrGetExisting_whenItemExists() {
        when(em.merge(testCardPackage)).thenThrow(constraintError);
        CardPackage pack = packageDao.saveOrGetExisting(testCardPackage);
        assertSame(pack, mockQueryResponse);
        assertNotSame(pack, expectedResult);
    }

    @Test
    public void getCardPackageByName() {
        String testName = "testName";
        CardPackage result = packageDao.getCardPackageByName(testName);
        //it creates query with name as parameter
        verify(em, times(1)).createNativeQuery(any(), eq(CardPackage.class));
        verify(createdQuery, times(1)).setParameter("name", testName);
        //it returns result of single query
        assertSame(result, mockQueryResponse);
    }

}