package com.tj.cardsagainsthumanity.core.game.handManager;

import com.tj.cardsagainsthumanity.models.cards.Card;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.*;

import static junit.framework.TestCase.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class GenericHandManagerTest {
    @Mock
    private DrawStrategy<Card> drawStrategy;
    @Mock
    private Player player1;
    @Mock
    private Player player2;
    @Mock
    private Player player3;
    @Mock
    private Card card1;
    @Mock
    private Card card2;
    @Mock
    private Card card3;
    private GenericHandManager<Card> handManager;

    @Before
    public void setUp() throws Exception {
        handManager = new GenericHandManager(3);
        handManager.addPlayer(player1);
        handManager.addPlayer(player2);
    }

    @Test
    public void getPlayers() {
        Set<Player> players = handManager.getPlayers();
        assertTrue(players.contains(player1));
        assertTrue(players.contains(player2));
        assertEquals(players.size(), 2);
    }

    @Test
    public void getCardsInHandForPlayer() {
        Set<Card> cardsToAdd = new HashSet<>(Arrays.asList(card1, card2));
        handManager.addCardsToHandForPlayer(player1, cardsToAdd);
        assertEquals(handManager.getCardsInHandForPlayer(player2), new HashSet<>());
        assertEquals(handManager.getCardsInHandForPlayer(player1), cardsToAdd);
    }

    @Test
    public void getCardsInHandForPlayer_whenPlayerNotAround() {
        assertEquals(handManager.getCardsInHandForPlayer(player3), new HashSet<>());
    }

    @Test
    public void getAllCardsInHand() {
        Map<Player, Set<Card>> map = new HashMap<>();
        handManager = new GenericHandManager(map, 3);
        assertSame(map, handManager.getAllCardsInHand());
    }

    @Test
    public void addPlayer() {
        handManager.addPlayer(player3);

        assertEquals(handManager.getPlayers().size(), 3);
    }

    @Test
    public void removePlayer() {
        handManager.removePlayer(player1);

        assertEquals(handManager.getPlayers().size(), 1);
        assertFalse(handManager.getPlayers().contains(player1));
    }

    @Test
    public void getHandSizeForPlayer() {
        Set<Card> cardsToAdd = new HashSet<>(Arrays.asList(card1, card2));
        handManager.addCardsToHandForPlayer(player1, cardsToAdd);
        assertEquals(handManager.getHandSizeForPlayer(player1), 2);
    }

    @Test
    public void removeCardsFromHand() {

        Set<Card> cardsToAdd = new HashSet<>(Arrays.asList(card1, card2, card3));
        handManager.addCardsToHandForPlayer(player1, cardsToAdd);
        assertEquals(handManager.getHandSizeForPlayer(player1), 3);
        handManager.removeCardsFromHand(player1, Arrays.asList(card1, card3));
        assertEquals(handManager.getHandSizeForPlayer(player1), 1);
        assertTrue(handManager.getPlayers().contains(player2));
    }

    @Test
    public void clearHandForPlayer() {
        Set<Card> cardsToAdd = new HashSet<>(Arrays.asList(card1, card2));
        handManager.addCardsToHandForPlayer(player1, cardsToAdd);
        assertEquals(handManager.getCardsInHandForPlayer(player1), cardsToAdd);
        handManager.clearHandForPlayer(player1);
        assertEquals(handManager.getCardsInHandForPlayer(player1), new HashSet<>());
    }

    @Test
    public void getMinCardsInHand() {
        assertEquals(handManager.getMinCardsInHand(), 3);
    }

    @Test
    public void getMaxCardsInHand() {
        assertEquals(handManager.getMaxCardsInHand(), 3);
    }

    private GenericHandManager<Card> createGenericHandManager() {
        Map<Player, Set<Card>> hands = new HashMap<>();
        hands.put(player1, new HashSet<>(Arrays.asList(card1)));
        hands.put(player2, new HashSet<>(Arrays.asList(card1, card2, card3)));
        hands = spy(hands);
        return new GenericHandManager<>(hands, 3);
    }

    @Test
    public void fillOutHandsForPlayers() {
        GenericHandManager<Card> manager = createGenericHandManager();
        Set<Card> cardsToDraw = new HashSet<>(Arrays.asList(card2, card3));
        when(drawStrategy.drawCards(2)).thenReturn(cardsToDraw);
        manager.fillOutHandsForAllPlayers(drawStrategy);
        Set<Card> hand = new HashSet(cardsToDraw);
        hand.addAll(manager.getCardsInHandForPlayer(player1));

        verify(manager.getAllCardsInHand(), times(1)).putIfAbsent(player1, hand);
        verify(drawStrategy, times(1)).drawCards(2);
        verify(drawStrategy, times(1)).drawCards(anyInt());
    }

    @Test
    public void addCardsToHandForPlayer() {
        Set<Card> cardsToAdd = new HashSet<>(Arrays.asList(card1, card2));
        handManager.addCardsToHandForPlayer(player1, cardsToAdd);
        assertEquals(handManager.getCardsInHandForPlayer(player1), cardsToAdd);
    }


    @Test
    public void addCardsToHandForPlayer_whenHandTooBig() {
        Set<Card> cardsToAdd = new HashSet<>(Arrays.asList(card1, card2, card3));
        handManager.addCardsToHandForPlayer(player1, cardsToAdd);
        try {
            handManager.addCardsToHandForPlayer(player1, cardsToAdd);
        } catch (IllegalStateException ex) {
            return;
        }
        fail("ExpectedErrror");
    }
}