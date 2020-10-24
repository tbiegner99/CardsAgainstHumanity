package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.dao.CardDao;
import com.tj.cardsagainsthumanity.models.cards.WhiteCard;
import com.tj.cardsagainsthumanity.models.gameplay.CardPlay;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.CardPlayRequest;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundWhiteCard;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.PlayCardCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.EmptyResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import com.tj.cardsagainsthumanity.server.protocol.message.Response;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class PlayCardCommandProcessorTest {
    @Mock
    CardDao cardDao;
    @Mock
    CommandContext context;
    @Mock
    PlayCardCommand command;
    @Mock
    Player player;
    @Mock
    GameDriver game;
    @Mock
    RoundDriver round;
    @Mock
    CardPlayRequest request;
    @Mock
    RoundWhiteCard card1;
    @Mock
    RoundWhiteCard card2;
    @Mock
    WhiteCard loadedCard1;
    @Mock
    WhiteCard getLoadedCard2;

    String messageId = "messageId";
    PlayCardCommandProcessor processor;
    Integer id1 = 1;
    Integer id2 = 2;

    @Before
    public void setUp() throws Exception {
        processor = new PlayCardCommandProcessor(cardDao);
        when(context.getCurrentGame()).thenReturn(Optional.of(game));
        when(context.getPlayer()).thenReturn(Optional.of(player));
        when(command.getArguments()).thenReturn(request);
        when(request.getCardsToPlay()).thenReturn(Arrays.asList(card1, card2));
        when(card1.getId()).thenReturn(id1);
        when(card2.getId()).thenReturn(id2);
        when(cardDao.getWhiteCard(id1)).thenReturn(loadedCard1);
        when(cardDao.getWhiteCard(id2)).thenReturn(getLoadedCard2);
        when(game.getCurrentRound()).thenReturn(round);
        when(command.getMessageId()).thenReturn(messageId);
    }

    @Test
    public void processMessage_whenGameIsValidState_playsCardsForPlayer() {
        Response response = processor.processMessage(command, context);
        assertEquals(response, EmptyResponse.NO_CONTENT.forMessage(messageId));
        verify(round,times(1)).playCards(player,Arrays.asList(loadedCard1,getLoadedCard2));
        verify(cardDao, times(1)).getWhiteCard(id1);
        verify(cardDao, times(1)).getWhiteCard(id2);
    }
    @Test
    public void processMessage_whenNoPlayer_returnsForbiddenResponse() {
        when(context.getPlayer()).thenReturn(Optional.empty());
        Response response = processor.processMessage(command, context);
        assertEquals(response, EmptyResponse.FORBIDDEN.forMessage(messageId));
    }

}