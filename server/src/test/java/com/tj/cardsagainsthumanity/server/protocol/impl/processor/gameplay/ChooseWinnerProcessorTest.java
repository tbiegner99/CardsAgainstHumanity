package com.tj.cardsagainsthumanity.server.protocol.impl.processor.gameplay;

import com.tj.cardsagainsthumanity.core.game.GameDriver;
import com.tj.cardsagainsthumanity.core.game.RoundDriver;
import com.tj.cardsagainsthumanity.models.cards.BlackCard;
import com.tj.cardsagainsthumanity.models.gameStatus.GameStatusFactory;
import com.tj.cardsagainsthumanity.models.gameStatus.RoundStatus;
import com.tj.cardsagainsthumanity.models.gameplay.GameRound;
import com.tj.cardsagainsthumanity.models.gameplay.Player;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.arguments.RoundCardPlay;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.command.gameplay.ChooseWinnerCommand;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.GameResponse;
import com.tj.cardsagainsthumanity.server.protocol.impl.message.response.RoundStatusResponse;
import com.tj.cardsagainsthumanity.server.protocol.message.CommandContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class ChooseWinnerProcessorTest {

    @Mock
    GameDriver mockCreatedDriver;
    @Mock
    RoundDriver mockRound;
    @Mock
    GameRound roundModel;
    @Mock
    CommandContext context;
    @Mock
    ChooseWinnerCommand message;
    @Mock
    Player currentPlayer;
    @Mock
    Player czar;
    @Mock
    BlackCard blackCard;
    @Mock
    RoundCardPlay play;
    @Mock
    GameStatusFactory gameStatusFactory;

    ChooseWinnerProcessor processor;
    GameResponse result;
    Integer winningId = 89;
    String messageId = "messageId";

    @Before
    public void beforeEach() {
        processor = new ChooseWinnerProcessor(gameStatusFactory);
        when(context.getCurrentGame()).thenReturn(Optional.of(mockCreatedDriver));
        when(context.getPlayer()).thenReturn(Optional.of(currentPlayer));
        when(message.getMessageId()).thenReturn(messageId);
        when(mockCreatedDriver.getCurrentRound()).thenReturn(mockRound);
        when(play.getId()).thenReturn(winningId);
        when(message.getArguments()).thenReturn(play);
        when(mockRound.getRound()).thenReturn(roundModel);
        when(roundModel.getCzar()).thenReturn(czar);
        when(roundModel.getBlackCard()).thenReturn(blackCard);
    }

    @Test
    public void processMessage() {
        RoundStatus expectedStatus = RoundStatus.fromRound(mockRound, currentPlayer);
        RoundStatusResponse expectedResult = new RoundStatusResponse(messageId, expectedStatus);
        result = processor.processMessage(message, context);
        verify(mockRound, times(1)).declareWinner(currentPlayer, winningId);
        assertEquals(result, expectedResult);
    }
}