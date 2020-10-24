package com.tj.cardsagainsthumanity.core.game.gameDriver;

import com.tj.cardsagainsthumanity.models.gameplay.Player;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class CzarGeneratorTest {
    @Mock
    private Player mockPlayer;
    @Mock
    private Player mockPlayer2;

    private CzarGenerator generator;

    @Before
    public void setUp() throws Exception {
        generator = new CzarGenerator(Arrays.asList(mockPlayer, mockPlayer2));
    }

    @Test
    public void hasNext() {
        boolean emptyListNext = new CzarGenerator(Arrays.asList()).hasNext();
        assertFalse(emptyListNext);
        assertTrue(generator.hasNext());
    }

    @Test
    public void next() {
        List<Player> czars = generator.getCzarOrder();
        for (int i = 0; i < 10; i++) {
            Player result = generator.next();
            Player expectedResult = i % 2 == 0 ? czars.get(0) : czars.get(1);
            assertSame(result, expectedResult);
        }
    }
}