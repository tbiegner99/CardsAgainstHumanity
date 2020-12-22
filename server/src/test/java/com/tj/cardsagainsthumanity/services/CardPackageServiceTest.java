package com.tj.cardsagainsthumanity.services;

import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.dao.CardPackageDao;
import com.tj.cardsagainsthumanity.dao.DeckDao;
import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.cards.CardPackage;
import com.tj.cardsagainsthumanity.models.cards.PackageImport;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.*;

@Ignore
@RunWith(MockitoJUnitRunner.class)
public class CardPackageServiceTest {

    @Mock
    private CardPackageDao dao;
    @Mock
    private CardDao cardDao;
    @Mock
    private DeckDao deckDao;
    @Mock
    private CardPackage testPackage;
    @Mock
    private Card card1;
    @Mock
    private Card card2;

    private Set<Card> cardsToImport;


    private CardPackageService service;

    @Before
    public void beforeEach() {
        MockitoAnnotations.initMocks(this);
        cardsToImport = new HashSet<>(Arrays.asList(card1, card2));
        service = new CardPackageService(deckDao, dao, cardDao);
        when(dao.deleteCardPackage(any())).thenReturn(testPackage);
        when(dao.saveCardPackage(any())).thenReturn(testPackage);
        when(dao.getCardPackageById(6)).thenReturn(testPackage);
        when(dao.saveOrGetExisting(testPackage)).thenReturn(testPackage);
    }

    @Test
    public void deleteCard() {
        CardPackage result = service.deleteCardPackage(testPackage);
        //it invokes dao
        verify(dao, times(1)).deleteCardPackage(testPackage);
        //it returns dao returned value
        assertSame(result, testPackage);
    }


    @Test
    public void createCardPackage() {
        CardPackage result = service.createCardPackage(testPackage);
        //it invokes dao
        verify(dao, times(1)).saveCardPackage(testPackage);
        //it returns dao returned value
        assertSame(result, testPackage);
    }


    @Test
    public void getCardPackageById() {
        CardPackage result = service.getCardPackageById(6);
        //it invokes dao
        verify(dao, times(1)).getCardPackageById(6);
        //it returns dao returned value
        assertSame(result, testPackage);
    }

    @Test
    public void importPackages() {
        PackageImport importObj = new PackageImport(testPackage, cardsToImport);
        Collection<PackageImport> packagesToImport = Arrays.asList(importObj, importObj);
        Collection<CardPackage> result = service.importPackages(packagesToImport);
        Collection<CardPackage> expectedResult = Arrays.asList(testPackage, testPackage);
        //it saves all the cards
        verify(cardDao, times(2)).saveCard(card1);
        verify(cardDao, times(2)).saveCard(card2);
        //it saves the package
        verify(dao, times(2)).saveOrGetExisting(testPackage);
        //it returns the saved packages
        assertEquals(result, expectedResult);
    }
}